import React, { useState, useEffect } from 'react';
import { MapContainer, NavigationLineDirective, NavigationLinesDirective, Popup, TileLayer, useMap } from 'react-leaflet';
import L from 'leaflet';
import CustomMarker from '../CustomMarker/CustomMarker';
import POSITION from './Местоположение карты.svg'
import USER from './User.svg'
import SmallBranch from '../SmallBranch/SmallBranch';
import { NavLink } from 'react-router';
import styles from './BranchListMap.module.css'

const fetchAllBranches = async (options) => {
    try {
        const response = await fetch('http://localhost:8080/branch', options);
        return await response.json();
    } catch (err) {
        console.error('Error fetching branches:', err);
        return null;
    }
};

const fetchNearestBranches = async (options) => {
    return await fetch('http://localhost:8080/branch/nearest', options)
        .then(response => response.json())
        .catch(err => console.error(err));
}

const BranchListMap = () => {
    const [userLocation, setUserLocation] = useState(null);
    const [mapReady, setMapReady] = useState(false);
    const [branches, setBranches] = useState([]);
    const [targetLocation, setTargetLocation] = useState(null);
    const [lineVisible, setLineVisible] = useState(false);

    useEffect(() => {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                const { latitude, longitude } = position.coords;
                setUserLocation([latitude, longitude]);
                setMapReady(true);
            },
            (error) => {
                console.error('Error getting user location:', error);
                setMapReady(true);
            }
        );

        const fetchBranches = async () => {
            const body = {
                "coordinate": null,
                "size": 100
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
                } catch (error) { 
                    console.error('Error fetching nearest branches:', error) 
                }
            }, (error) => { 
                console.error('Error getting user location:', error) 
            });
        };

        fetchBranches();
    }, []);

    const UserLocationButton = () => {
        const map = useMap();
        
        const handleGoToUserLocation = () => {
            if (userLocation) {
                map.flyTo(userLocation, 13);
            }
        };

        return (
            <button
                onClick={handleGoToUserLocation}
                style={{
                    position: 'absolute',
                    top: '90px',
                    left: '12px',
                    zIndex: 1000,
                    padding: '5px',
                }}
            >
                <img
                    src="https://www.svgrepo.com/show/315096/current-location.svg"
                    alt="logo"
                    width="16px"
                />
            </button>
        );
    };

    const stopMapScroll = (element) => {
        if (element) {
            L.DomEvent.disableScrollPropagation(element);
        }
    };

    return (
        <div
            style={{
                height: '87vh',
                width: '100%',
                margin: 'auto auto',
                borderRadius: '15px',
                overflow: 'hidden',
            }}
        >
            {mapReady && (
                <MapContainer
                    center={userLocation || [51.505, -0.09]}
                    zoom={userLocation ? 13 : 3}
                    scrollWheelZoom={true}
                    style={{
                        height: '110%',
                        width: '98%',
                        borderRadius: '15px',
                        position: 'relative',
                        margin: '0 auto'
                    }}
                >
                    <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
                    {userLocation && <CustomMarker key={'user'} position={userLocation} icon={USER}/>}

                    {branches.length > 0 &&
                        branches.map((branch, index) => {
                            const { x, y } = branch.coordinate || {};
                            
                            if (x !== undefined && y !== undefined) {
                                const coordinate = [x, y];
                                return (
                                    <CustomMarker
                                        key={index}
                                        icon={POSITION}
                                        position={coordinate}
                                    >
                                        <Popup>
                                            {branch.name || 'Branch'}
                                        </Popup>
                                    </CustomMarker>
                                );
                            }
                            return null;
                        })}

                    <UserLocationButton />
                    <div className={styles.branches}
                        ref={stopMapScroll}
                    >
                        {branches && branches.map((branch, index) => {
                            return  <NavLink to={`/${branch.id}`} style={{all: 'unset', width: '100%'}}>
                                        <SmallBranch id={branch.id} name={branch.name}  mark={branch.mark} distance={branch.distance} />
                                    </NavLink>
                        })}    
                    </div>
                    <NavigationLinesDirective>
                        <NavigationLineDirective 
                            visible={lineVisible}
                            latitude={[34.06062, 40.724546]}
                            longitude={[-118.330491, -73.850344]}
                            color="blue"
                            angle={90}
                            width={5}/>
                    </NavigationLinesDirective>
                </MapContainer>
            )}
        </div>
    );
};

export default BranchListMap;
