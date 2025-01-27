import React, { useEffect, useState } from 'react';
import styles from './ServiceList.module.css'
import { NavLink } from 'react-router';
import SmallService from '../SmallService/SmallService';

const fetchServices = async (page, size, options) => {
    return await fetch(`http://localhost:8082/service/${page}/${size}`, options)
        .then(response => response.json())
        .catch(err => console.error(err));
}

const ServiceList = ({ page, size, pagesCount}) => {
    const [pages, setPages] = useState(0);
    const [services, setServices] = useState([]);

    useEffect(() => {
        const fetchSerivesAsync = async () => {
            const options = {
                method: 'GET',
                headers: { 'Content-Type': 'application/json' }
            };

            try {
                const response = await fetchServices(page, size, options);
                if (response) {
                    console.log(response.pages);
                    
                    setPages(response.pages)
                    setServices(response.services);
                }
            } catch (error) { 
                console.error('Error fetching popular services:', error) 
            }
        
        };

        fetchSerivesAsync();
    }, [])

    return (
        <div className={styles.services_container}>
            <h1>Все услуги</h1>
            <div className={styles.service_list_container}>
                {services && services.map((service, index) => {
                    console.log(service);
                    
                    // <NavLink key={service.id} to={"/"} style={{all: 'unset'}}>
                        return (
                        <div className={styles.service_container}>
                            <SmallService 
                            key={service.id} 
                            name={service.name} 
                            description={service.description} 
                            monthUsages={service.monthUsages}/>
                        </div>  )
                    // </NavLink>
                })}<div className={styles.pages}> 
                {pages && Array.from({ length: pagesCount }, (_, i) => (
                    <div key={i} className={styles.page}>
                        {i + 1}
                    </div>
                ))}
            </div>
            </div>
            
        </div>
        
    );
}

export default ServiceList;
