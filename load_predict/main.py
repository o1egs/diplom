import json
import numpy as np
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error
from datetime import datetime

# Функция для вычисления дня недели по дате
def get_day_of_week(date_str):
    date_obj = datetime.strptime(date_str, "%d:%m:%Y")
    return date_obj.weekday()  # Понедельник = 0, Воскресенье = 6

# Функция для предсказания нагрузки
def predict_daily_load(input_json, predict_date):
    # Загружаем данные из JSON
    data = json.loads(input_json)

    # Собираем данные из массива записей
    X = []
    y = []
    for record in data:
        day_of_week = get_day_of_week(record["date"])
        for item in record["load"]:
            hour = int(item["time"].split(":")[0])
            X.append([hour, day_of_week])
            y.append(item["load"])

    # Преобразуем в массивы numpy для обучения модели
    X = np.array(X)
    y = np.array(y)

    # Разделяем данные на обучающую и тестовую выборки
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

    # Создаем и обучаем модель линейной регрессии
    model = LinearRegression()
    model.fit(X_train, y_train)

    # Оцениваем качество модели
    y_pred = model.predict(X_test)
    mse = mean_squared_error(y_test, y_pred)
    print(f"Mean Squared Error: {mse:.2f}")

    # Предсказание нагрузки на весь день (с 8:00 до 18:00) для указанной даты
    predict_day = get_day_of_week(predict_date)
    hours = np.arange(8, 19)
    day_of_week_feature = [predict_day] * len(hours)
    prediction_input = np.column_stack((hours, day_of_week_feature))
    predicted_load = model.predict(prediction_input)

    # Формируем результат в виде JSON
    result = {
        "date": predict_date,
        "predicted_load": [
            {"time": f"{hour}:00", "load": round(load, 2) if load > 0 else 0}
            for hour, load in zip(hours, predicted_load)
        ]
    }

    return json.dumps(result, indent=4)

# Пример входных данных
input_json = """
[
    {
        "date": "11:11:2024",
        "load": [
            {"time": "8:00", "load": 10},
            {"time": "9:00", "load": 10},
            {"time": "10:00", "load": 10},
            {"time": "11:00", "load": 10},
            {"time": "12:00", "load": 10},
            {"time": "13:00", "load": 10},
            {"time": "14:00", "load": 10},
            {"time": "15:00", "load": 10},
            {"time": "16:00", "load": 10},
            {"time": "17:00", "load": 10},
            {"time": "18:00", "load": 10}
        ]
    },
    {
        "date": "12:11:2024",
        "load": [
            {"time": "8:00", "load": 6},
            {"time": "9:00", "load": 7},
            {"time": "10:00", "load": 8},
            {"time": "11:00", "load": 5},
            {"time": "12:00", "load": 4},
            {"time": "13:00", "load": 6},
            {"time": "14:00", "load": 7},
            {"time": "15:00", "load": 5},
            {"time": "16:00", "load": 4},
            {"time": "17:00", "load": 3},
            {"time": "18:00", "load": 2}
        ]
    },
    {
        "date": "13:11:2024",
        "load": [
            {"time": "8:00", "load": 1},
            {"time": "9:00", "load": 1},
            {"time": "10:00", "load": 1},
            {"time": "11:00", "load": 1},
            {"time": "12:00", "load": 1},
            {"time": "13:00", "load": 1},
            {"time": "14:00", "load": 1},
            {"time": "15:00", "load": 10},
            {"time": "16:00", "load": 0},
            {"time": "17:00", "load": 0},
            {"time": "18:00", "load": 0}
        ]
    },
    {
        "date": "14:11:2024",
        "load": [
            {"time": "8:00", "load": 6},
            {"time": "9:00", "load": 7},
            {"time": "10:00", "load": 8},
            {"time": "11:00", "load": 5},
            {"time": "12:00", "load": 4},
            {"time": "13:00", "load": 6},
            {"time": "14:00", "load": 7},
            {"time": "15:00", "load": 5},
            {"time": "16:00", "load": 4},
            {"time": "17:00", "load": 3},
            {"time": "18:00", "load": 2}
        ]
    },{
        "date": "15:11:2024",
        "load": [
            {"time": "8:00", "load": 0},
            {"time": "9:00", "load": 0},
            {"time": "10:00", "load": 0},
            {"time": "11:00", "load": 0},
            {"time": "12:00", "load": 0},
            {"time": "13:00", "load": 0},
            {"time": "14:00", "load": 0},
            {"time": "15:00", "load": 10},
            {"time": "16:00", "load": 0},
            {"time": "17:00", "load": 0},
            {"time": "18:00", "load": 0}
        ]
    }
]
"""

# Запуск функции и вывод результата

for i in range(18, 24):
    predict_date = f'{i}:11:2024'
    predicted_json = predict_daily_load(input_json, predict_date)
    print(predicted_json + "\n")