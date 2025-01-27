import React, { useState, useEffect, useCallback } from 'react';
import styles from './NearestBranches.module.css'
import SmallBranch from '../SmallBranch/SmallBranch';
import { NavLink } from 'react-router';

const fetchNearestBranches = async (options) => {
    return await fetch('http://localhost:8080/branch/nearest', options)
        .then(response => response.json())
        .catch(err => console.error(err));
}

const NearestBranches = React.memo(() => {
    const [branches, setBranches] = useState([]);

    useEffect(() => {
        const fetchBranches = async () => {
            const body = {
                "coordinate": null,
                "size": 3
            };
    
            navigator.geolocation.getCurrentPosition(async (position) => {
                const { latitude, longitude } = position.coords;
                body.coordinate = {
                    "x": latitude,
                    "y": longitude 
                };
    
                const options = {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(body)
                };
    
                try {
                    const response = await fetchNearestBranches(options);
                    if (response) {
                        setBranches(response["nearestBranches"]);
                    }
                } catch (error) { console.error('Error fetching nearest branches:', error) }
            }, (error) => { console.error('Error getting user location:', error) });
        };

        fetchBranches();
    }, []);

    return (
        <div className={styles.small_container}>
            <h2>Ближайшие отделения</h2>
            <div className={styles.branch_small_list}>
                {branches.map((element, index) => (
                    <button className={styles.branch_small} key={element.id}>
                        <NavLink to={`/${element.id}`} style={{all: 'unset', width: '100%'}}>
                            <SmallBranch id={element.id} name={element.name} mark={element.mark} distance={element.distance}/>
                        </NavLink>
                    </button>
                ))}
            </div>
            <div className={styles.more_button_container}>
                <NavLink to={"/branches"} className={styles.more_button} >
                    <div>Больше отделений</div>
                </NavLink>
            </div>
            
        </div>
        
    );
})

export default NearestBranches;
