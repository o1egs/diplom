import React from 'react';
import styles from './SmallService.module.css'

const SmallService = ({ id, name, description, monthUsages }) => {
    return (
        <div className={styles.branch_small} key={id}>
                <div className={styles.info}>
                    <div className={styles.avatar}>
                        
                    </div>
                        <div className={styles.about}>
                            <div className={styles.name}>
                                {name}
                            </div>
                            <div className={styles.distance}>
                                    {description}
                                </div>
                        </div>
                    </div>
                    <div className={styles.mark}>
                        <div className={styles.text}>Выполнено</div> 
                        <div className={styles.count}>{monthUsages}</div>   
                    </div>
            </div>
    );
}

export default SmallService;
