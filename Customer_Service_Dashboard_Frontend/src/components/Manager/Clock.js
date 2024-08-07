import React, { useState, useEffect } from 'react';

function getCurrentTime() {
    const date = new Date();
    const hours = Number(date.toLocaleString('en-US', { hour: 'numeric', hour12: false, timeZone: 'Asia/Kolkata' }));
    const minutes = date.toLocaleString('en-US', { minute: 'numeric', timeZone: 'Asia/Kolkata' });
    const seconds = date.toLocaleString('en-US', { second: 'numeric', timeZone: 'Asia/Kolkata' });
    return { hours, minutes, seconds };
}

function Clock() {
    const [time, setTime] = useState(getCurrentTime());

    useEffect(() => {
        const interval = setInterval(() => {
            setTime(getCurrentTime());
        }, 1000);
        return () => clearInterval(interval);
    }, []);

    return (
        <div>
            <h2>Current Time: {time.hours}:{time.minutes}:{time.seconds}</h2>
        </div>
    );
}

export default Clock;
