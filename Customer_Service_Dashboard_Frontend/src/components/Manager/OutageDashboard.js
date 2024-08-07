import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import './OutageDashboard.css';

const redIcon = new L.Icon({
    iconUrl: './Images/redLocation.png',
    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
    iconSize: [45, 55],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
});

const defaultIcon = new L.Icon({
    iconUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png',
    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
    iconSize: [25, 41],
});

delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
    iconRetinaUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon-2x.png',
    iconUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png',
    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png',
});

function getCurrentTime() {
    const date = new Date();
    const hours = Number(date.toLocaleString('en-US', { hour: 'numeric', hour12: false, timeZone: 'Asia/Kolkata' }));
    const minutes = date.toLocaleString('en-US', { minute: 'numeric', timeZone: 'Asia/Kolkata' });
    const seconds = date.toLocaleString('en-US', { second: 'numeric', timeZone: 'Asia/Kolkata' });

    return { hours, minutes, seconds };
}

export default function Outage() {
    const [outageDetails, setOutageDetails] = useState([]);
    const [activeLocation, setActiveLocation] = useState(null);

    const locations = [
        { lat: 17.385044, lng: 78.486671, description: 'Hyderabad Outage', name: 'Hyderabad' },
        { lat: 12.971599, lng: 77.594566, description: 'Bangalore Outage', name: 'Bangalore' },
        { lat: 28.704060, lng: 77.102493, description: 'Delhi Outage', name: 'New Delhi' },
        { lat: 13.087840, lng: 80.278718, description: 'Chennai Outage', name: 'Chennai' },
        { lat: 19.076090, lng: 72.877426, description: 'Mumbai Outage', name: 'Mumbai' },
        { lat: 22.5744, lng: 88.3629, description: 'Kolkata Outage', name: 'Kolkata' }
    ];

    useEffect(() => {
        axios.get('http://localhost:8089/outage/get')
            .then(response => {
                const data = response.data;
                setOutageDetails(data);
            })
            .catch(error => {
                console.log(error);
            });
    }, []);

    useEffect(() => {
        const interval = setInterval(() => {
            const { hours } = getCurrentTime();
            setActiveLocation(null); 
            outageDetails.forEach(data => {
                const startHour = parseInt(data.start_time.split(':')[0]);
                const endHour = parseInt(data.end_time.split(':')[0]);
                if (hours >= startHour && hours <= endHour) {
                    setActiveLocation(data.location);
                }
            });
        }, 3000);
        return () => clearInterval(interval); 
    }, [outageDetails,activeLocation]);

    return (
        <div className="outage-dashboard">
            <div className="outage-details glass-card1">
                <h3 className="outage-heading">Outage Details</h3>
                <table className="outage-table">
                    <thead>
                        <tr>
                            <th>Location</th>
                            <th>Start Time</th>
                            <th>End Time</th>
                        </tr>
                    </thead>
                    <tbody>
                        {outageDetails.map((data, index) => (
                            <tr key={index}>
                                <td style={{ color: activeLocation === data.location ? 'red' : 'white' }}>
                                    {data.location}
                                </td>
                                <td>{data.start_time}</td>
                                <td>{data.end_time}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <div className="outage-map glass-card1">
                <h3 className="outage-heading">Outage Locations</h3>
                <MapContainer center={[20.5937, 78.9629]} zoom={5} style={{ height: '500px', width: '100%' }} attributionControl={false}>
                    <TileLayer
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    />
                    {locations.map((location, index) => (
                        <Marker
                            key={index}
                            position={[location.lat, location.lng]}
                            icon={activeLocation === location.name ? redIcon : defaultIcon}
                            animationDuration={500}
                            animate={true}
                        >
                            {activeLocation === location.description && (
                                <Popup>{location.description}</Popup>
                            )}
                        </Marker>
                    ))}
                </MapContainer>
            </div>
        </div>
    );
}
