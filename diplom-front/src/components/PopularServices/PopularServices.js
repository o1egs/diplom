import React, { useState, useEffect, useCallback } from 'react';
import styles from './PopularServices.module.css'
import SmallBranch from '../SmallBranch/SmallBranch';
import { NavLink } from 'react-router';
import SmallService from '../SmallService/SmallService';

const COUNT = 3;

const fetchPopularServices = async (count, options) => {
    return await fetch(`http://localhost:8082/service/popular/${count}`, options)
        .then(response => response.json())
        .catch(err => console.error(err));
}

const PopularServices = () => {
    const [services, setServices] = useState([]);
    
        useEffect(() => {
            const fetchBranches = async () => {
                const options = {
                    method: 'GET',
                    headers: { 'Content-Type': 'application/json' }
                };
    
                try {
                    const response = await fetchPopularServices(COUNT, options);
                    if (response) {
                        setServices(response);
                    }
                } catch (error) { 
                    console.error('Error fetching popular services:', error) 
                }
            
            };
    
            fetchBranches();
        }, []);
    return (
        <div className={styles.small_container}>
            <h2>Популярные услуги</h2>
            <div className={styles.branch_small_list}>
                {services.map((element, index) => (
                        // <NavLink to={`/${element.id}`} style={{all: 'unset', width: '100%'}}>
                            <SmallService key={element.id} id={element.id} name={element.name} description={element.description} monthUsages={element.monthUsages}/>
                        // </NavLink>
                ))}
            </div>
            <div className={styles.more_button_container}>
                <NavLink to={"/branches"} className={styles.more_button} >
                    <div>Больше услуг</div>
                </NavLink>
            </div>
        </div>
    );
}

export default PopularServices;
