import React, { useState, useEffect, useCallback } from 'react';
import styles from '../PopularServices/PopularServices.module.css'
import { NavLink } from 'react-router';
import SmallService from '../SmallService/SmallService';

const COUNT = 3;

const fetchPopularServices = async (branchId, count, options) => {
    return await fetch(`http://localhost:8080/provided/${branchId}/${count}`, options)
        .then(response => response.json())
        .catch(err => console.error(err));
}

const ProvidedServices = ( {branchId} ) => {
    const [services, setServices] = useState([]);
    
        useEffect(() => {
            const fetchBranches = async () => {
                const options = {
                    method: 'GET',
                    headers: { 'Content-Type': 'application/json' }
                };
    
                try {
                    const response = await fetchPopularServices(branchId, COUNT, options);
                    if (response) {
                        
                        setServices(response);
                    }
                } catch (error) { 
                    console.error('Error fetching popular services:', error) 
                }
            
            };
    
            fetchBranches();

            console.log(services);
            
        }, []);
    return (
        <div className={styles.small_container}>
            <h2>Оказываемые услуги</h2>
            <div className={styles.branch_small_list}>
                {services.map((element, index) => (
                        // <NavLink to={`/${element.id}`} style={{all: 'unset', width: '100%'}}>
                            <SmallService id={element.service.id} name={element.service.name} description={element.service.description} monthUsages={element.service.monthUsages}/>
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

export default ProvidedServices;
