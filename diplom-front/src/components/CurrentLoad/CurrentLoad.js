import React, { useState, useEffect } from 'react';

const fetchCurrentLoad = async (branchId, options) => {
    return await fetch(`http://localhost:8080/branch/load/current/${branchId}`, options)
        .then((response) => response.text())
        .then((text) => parseFloat(text))    
        .catch(err => console.error(err));
}

const CurrentLoad = ({ branchId }) => {
    const [currentLoad, setCurrentLoad] = useState();
    const [loading, setLoading] = useState(); 

    useEffect(() => {
        const fetchCurrent = async () => {
            const options = {
                method: 'GET',
            };

            const data = await fetchCurrentLoad(branchId, options);
            setCurrentLoad(data);
        };

        fetchCurrent();
    }, [branchId]);

    return (
        <div>
            {`Текущая нагрузка: ${currentLoad ? currentLoad: ' неизвестна'}`}
        </div>
    );
}

export default CurrentLoad;
