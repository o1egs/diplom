import React from 'react';
import { useParams } from 'react-router';
import styles from './BranchPage.module.css'
import Branch from '../../components/Branch/Branch';
import ProvidedServices from '../../components/ProvidedServices/ProvidedServices';

const BranchPage = () => {
    let { branchId } = useParams();

    return (
        <div className={styles.main_container}>
            <Branch branchId={branchId}/>
            <ProvidedServices branchId={branchId}/>
        </div>
    );
}

export default BranchPage;
