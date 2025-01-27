from flask import Flask, request, jsonify
import json
import numpy as np
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error
from datetime import datetime

app = Flask(__name__)

# Функция для вычисления дня недели по дате
def get_day_of_week(date):
    return date.weekday()  # Понедельник = 0, Воскресенье = 6

# Функция для обучения модели
def train_model(X, y):
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
    model = LinearRegression()
    model.fit(X_train, y_train)
    mse = mean_squared_error(y_test, model.predict(X_test))
    return model, mse

# Функция для предсказания
def predict_load(model, predict_date):
    predict_day = get_day_of_week(predict_date)
    hours = np.arange(8, 19)
    day_of_week_feature = [predict_day] * len(hours)
    prediction_input = np.column_stack((hours, day_of_week_feature))
    predicted_load = model.predict(prediction_input)

    result = {
        "date": predict_date,
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
        
        
        day_of_week = get_day_of_week(datetime(int(date_[0]), int(date_[1]), int(date_[2])))
        for item in record["load"]:
            hour = int(item["time"].split(":")[0])  # Исправлено split(":")[0]
            X.append([hour, day_of_week])
            y.append(item["load"])
    return np.array(X), np.array(y)

# Создание API для предсказания
@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Получение данных из запроса
        input_data = request.json.get('data')
        date = request.json.get('predict_date')
        print(date[0], date[1], date[2])
        predict_date = datetime(int(date[0]), int(date[1]), int(date[2]))
        print(predict_date)

        if not input_data or not predict_date:
            return jsonify({"error": "Missing 'data' or 'predict_date' in the request"}), 400

        # Подготовка данных и обучение модели
        X, y = prepare_data(input_data)  # Передаем разобранный JSON
        model, mse = train_model(X, y)

        # Получение предсказания
        prediction = predict_load(model, predict_date)
        prediction["mse"] = mse

        return jsonify(prediction), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 400

if __name__ == '__main__':
    app.run(debug=True)
