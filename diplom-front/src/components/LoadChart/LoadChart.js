import React, { useState, useEffect } from 'react';
import { Bar } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from 'chart.js';

// Регистрация компонентов Chart.js
ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const fetchPrediction = async (options) => {
    return await fetch('http://localhost:8081/load/predict', options)
        .then(response => response.json())
        .catch(err => console.error(err));
};

const LoadChart = React.memo(({ branchId }) => {
    const [predict, setPredict] = useState([]);

    useEffect(() => {
        const fetchPredictions = async () => {
            const body = {
                branchId: branchId,
                date: new Date().toISOString().split('T')[0], // Только дата
            };

            const options = {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(body),
            };

            const response = await fetchPrediction(options);

            if (response && response.predicted_load) {
                setPredict(response.predicted_load);
            }
        };

        if (branchId) {
            fetchPredictions();
        }
    }, [branchId]);

    // Определение цветов на основе значения загрузки
    const getColor = (load) => {
        if (load >= 9) return 'red'; // Очень высокая нагрузка
        if (load >= 6) return 'orange'; // Высокая нагрузка
        if (load >= 3) return 'yellow'; // Средняя нагрузка
        return 'green'; // Низкая нагрузка
    };

    // Подготовка данных для графика
    const data = {
        labels: predict.map(item => item.time), // Метки времени
        datasets: [
            {
                label: 'Предполагаемая нагрузка',
                data: predict.map(item => item.load),
                backgroundColor: predict.map(item => getColor(item.load)), // Цвета столбцов
            },
        ],
    };

    const options = {
        responsive: true,
        plugins: {
            legend: {
                display: false, // Скрываем легенду
            },
            title: {
                display: true,
                text: 'Предполагаемая нагрузка',
                font: {
                    family: "Open Sans"
                } 
            },
        },
        scales: {
            x: {
                title: {
                    display: true,
                    text: 'Время',
                    font: {
                        family: "Open Sans"
                    } 
                },
            },
            y: {
                title: {
                    display: true,
                    text: 'Нагрузка',
                    font: {
                        family: "Open Sans"
                    } 
                },
                beginAtZero: true,
                max: 10,
            },
        },
    };

    return (
        <div style={{
            width: '100%',
            height: 400 
        }}>
            {branchId ? (
                <Bar data={data} options={options}/>
            ) : (
                <p>Укажите branchId для отображения графика.</p>
            )}
        </div>
    );
});

export default LoadChart;
