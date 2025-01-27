import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router';
import styles from "./Branch.module.css"
import CurrentLoad from "../CurrentLoad/CurrentLoad";
import AverageLoad from "../AverageLoad/AverageLoad"
import LoadChart from '../LoadChart/LoadChart';
import SmallBranch from '../SmallBranch/SmallBranch';
import CurrentTime from '../CurrentTime/CurrentTime';

const fetchBrancheById = async (branchId, options) => {
    return await fetch(`http://localhost:8080/branch/${branchId}`, options)
        .then((response) => response.json()) 
        .catch(err => console.error(err));
}

const fetchDistanceById = async (branchId, options) => {
    return await fetch(`http://localhost:8080/branch/distance/${branchId}`, options)
        .then((response) => response.text())
        .catch(err => console.error(err));
}


const Branch = ({ branchId }) => {
    const [branch, setBranch] = useState();
    const [distance, setDistance] = useState();

    useEffect(() => {
            const fetchBranch = async () => {
                const options = {
                    method: 'GET',
                    headers: { 'Content-Type': 'application/json' }
                };
    
                const response = await fetchBrancheById(branchId, options);
                
                if (response) {
                    setBranch(response);
                }
            };

            const fetchDistance = async () => {
                navigator.geolocation.getCurrentPosition(async (position) => {
                    const { latitude, longitude } = position.coords;
                    const body = {
                        "x": latitude,
                        "y": longitude 
                    };
        
                    const options = {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(body)
                    };
        
                    try {
                        const response = await fetchDistanceById(branchId, options);
                
                    if (response) {
                        setDistance(response);
                    }
                    } catch (error) { console.error('Error fetching nearest branches:', error) }
                }, (error) => { console.error('Error getting user location:', error) });
            }
    
            if( branchId ) {
                fetchBranch();
                fetchDistance();           
            }
        }, [branchId]);

    return (
        branch ? <div className={styles.small_container}>
                <SmallBranch name={branch.name} mark={branch.mark} distance={distance}/>
                <CurrentTime/>
                <CurrentLoad branchId={ branchId }/>
                <AverageLoad branchId={ branchId }/>
                <LoadChart branchId={ branchId }/>
            </div> : 
            
            <div></div>
    );
}

export default Branch;
