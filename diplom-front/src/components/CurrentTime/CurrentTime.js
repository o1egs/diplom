import React, { useEffect, useState } from 'react';

const CurrentTime = () => { 
    const [time, setTime] = useState('');

    useEffect(() => { 
        const updateTime = () => {
            const date = new Date(); 
            const hours = String(date.getHours()).padStart(2, '0'); // Добавляем ноль перед часами, если нужно
            const minutes = String(date.getMinutes()).padStart(2, '0'); // Добавляем ноль перед минутами, если нужно
            setTime(`${hours}:${minutes}`); 
        };

        updateTime(); // Устанавливаем начальное время

        const intervalId = setInterval(updateTime, 60000); // Обновляем время каждую минуту

        return () => clearInterval(intervalId); // Очищаем интервал при размонтировании компонента
    }, []); 

    return ( 
        <h2> 
            {time} 
        </h2> 
    ); 
}; 

export default CurrentTime;

