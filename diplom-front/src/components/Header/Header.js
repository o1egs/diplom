import React from 'react';
import styles from './Header.module.css'
import icon from './Events.svg'

const PLACEHOLDER = 'Услуги, отделения и др'

const Header = () => {
    return (
        <div className={styles.header_container}>
            <div className={styles.header}>
                <div className={styles.header_left}>
                    <img src={icon} className={styles.icon}></img>
                <h1>NeUSLUGI</h1>
                </div>
                
                <input 
                    className={styles.search} 
                    type="search" 
                    placeholder={PLACEHOLDER} 
                />
            </div>
            <div className={styles.search_fallback}>
                <input 
                    className={styles.search} 
                    type="search" 
                    placeholder={PLACEHOLDER} 
                />
            </div>
        </div>


    );
}

export default Header;
