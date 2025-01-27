from flask import Flask, request, jsonify
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.metrics import mean_squared_error
from datetime import datetime
import tensorflow as tf
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, Dropout

app = Flask(__name__)

# Функция для вычисления дня недели по дате
def get_day_of_week(date):
    return date.weekday()  # Понедельник = 0, Воскресенье = 6

# Функция для обучения модели
def train_model(X, y):
    scaler = StandardScaler()
    X_scaled = scaler.fit_transform(X)

    X_train, X_test, y_train, y_test = train_test_split(X_scaled, y, test_size=0.2, random_state=42)

    # Создание модели
    model = Sequential([
        tf.keras.Input(shape=(X_train.shape[1],)),  # Определяем входные данные
        Dense(64, activation='relu'),
        Dropout(0.2),
        Dense(32, activation='relu'),
        Dropout(0.2),
        Dense(1, activation='linear')
    ])
    model.compile(optimizer='adam', loss='mse')

    # Обучение модели
    model.fit(X_train, y_train, epochs=50, batch_size=16, verbose=0)

    # Оценка модели
    y_pred = model.predict(X_test)
    mse = mean_squared_error(y_test, y_pred)
    return model, scaler, mse


# Функция для предсказания
def predict_load(model, scaler, predict_date):
    predict_day = get_day_of_week(predict_date)
    hours = np.arange(8, 19)
    day_of_week_feature = [predict_day] * len(hours)
    month = predict_date.month
    season = (month % 12 + 3) // 3  # Сезоны: 1 - зима, 2 - весна, 3 - лето, 4 - осень
    season_feature = [season] * len(hours)

    prediction_input = np.column_stack((hours, day_of_week_feature, season_feature))
    prediction_input_scaled = scaler.transform(prediction_input)
    predicted_load = model.predict(prediction_input_scaled).flatten()

    result = {
        "date": predict_date.strftime('%Y-%m-%d'),
        "predicted_load": [
            {"time": f"{hour}:00", "load": round(load, 2) if load > 0 else 0}
            for hour, load in zip(hours, predicted_load)
        ]
    }
    return result

# Функция для подготовки данных
def prepare_data(data):
    X = []
    y = []
    for record in data:
        date_ = record["date"]
        date_obj = datetime(int(date_[0]), int(date_[1]), int(date_[2]))
        day_of_week = get_day_of_week(date_obj)
        month = date_obj.month
        season = (month % 12 + 3) // 3

        for item in record["load"]:
            hour = int(item["time"].split(":")[0])
            X.append([hour, day_of_week, season])
            y.append(item["load"])
    return np.array(X), np.array(y)

# Создание API для предсказания
@app.route('/predict', methods=['POST'])
def predict():
    try:
        input_data = request.json.get('data')
        date = request.json.get('predict_date')
        predict_date = datetime(int(date[0]), int(date[1]), int(date[2]))

        if not input_data or not predict_date:
            return jsonify({"error": "Missing 'data' or 'predict_date' in the request"}), 400

        # Подготовка данных и обучение модели
        X, y = prepare_data(input_data)
        model, scaler, mse = train_model(X, y)

        # Получение предсказания
        prediction = predict_load(model, scaler, predict_date)
        prediction["mse"] = mse

        return jsonify(prediction), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 400

if __name__ == '__main__':
    app.run(debug=True)
