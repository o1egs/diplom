import React from 'react';
import styles from "./SmallBranch.module.css"

const getColorFromScore = (score) => {
    if (score <= 3) {
        return 'rgb(206, 0, 0)'; // Красный
    } else if (score >=3 && score < 4) {
        return 'rgb(216, 212, 0)'; // Жёлтыйz
    } else if (score >= 4) {
        return 'rgb(63, 185, 47)'; // Зелёный
    }
};


function getRandomFloat(min, max) {
    return Math.random() * (max - min) + min;
}

const toggleDistanse = (distance) => {
    if(distance >= 0) {
        if(distance < 1000) {
            return `${Math.round(distance)}м  от ваc`
        } else {
            return `${Math.round(distance / 1000)}км  от ваc`
        }
    } return ""
} 

const SmallBranch = ({ id, name, distance, mark, onClick }) => {
    return (
            <div onClick={onClick} className={styles.branch_small} key={id}>
                <div className={styles.info}>
                    <div className={styles.avatar}>
                        
                    </div>
                        <div className={styles.about}>
                            <div className={styles.name}>
                                {name}
                            </div>
                            <div className={styles.distance}>
                                    {toggleDistanse(distance)}
                                </div>
                        </div>
                    </div>
                    <div className={styles.mark}>
                        <div className={styles.text}>Оценка</div> 
                            {(() => {
                                let randomColor = getColorFromScore(mark); 
                                return (
                                    <div className={styles.count} style={{ color: randomColor }}>
                                            {mark} {}
                                    </div>
                                );
                            })()} {}    
                    </div>
            </div>
    );
}

export default SmallBranch;
