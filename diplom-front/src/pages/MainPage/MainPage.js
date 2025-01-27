import React from 'react';
import styles from './MainPage.module.css'
import NearestBranches from '../../components/NearestBranches/NearestBranches';
import PopularServices from '../../components/PopularServices/PopularServices';

const MainPage = () => {
    return (
        <div className={styles.main_container}>
            <NearestBranches/>
            <PopularServices/>
        </div>
    );
}

export default MainPage;
