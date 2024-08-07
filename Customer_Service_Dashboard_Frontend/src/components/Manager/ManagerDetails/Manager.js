

import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import './Manager.css';
import Loading from '../../General/Loading';
import Modal from '../../General/Modal';
import { handleDelete } from '../../General/ServiceFunctions';
import { toast } from 'react-toastify';

function Manager() {
    const { managerId } = useParams();
    const [isLoading, setIsLoading] = useState(true);
    const [manager, setManager] = useState({});
    const [showModal, setShowModal] = useState(false);
    const navigate = useNavigate()

    useEffect(() => {
        const fetchManagerData = async () => {
            try {
                const response = await axios.get(`http://localhost:8086/employee/getManager/${managerId}`);
                setManager(response.data);
            } catch (err) {
                console.error(err.response.data);
                toast.error(err.response.data)
            } finally {
                setIsLoading(false);
            }
        };

        fetchManagerData();
    }, [managerId]);

    const openModal = () => {
        setShowModal(true);
    };

    const closeModal = () => {
        setShowModal(false);
    };

    const confirmDelete = () => {
        handleDelete(manager.empId);
        closeModal();
        navigate("/admin/list")
    };

    if (isLoading) {
        return <Loading />;
    }

    return (
        <div className="manager-container">
            <h2>Manager Details</h2>
            <div className="manager-details">
            <div className="manager-section">
            <h3>Manager Basic Details</h3>
            <div className="detail">
                <h4><b>Employee Id:</b></h4>
                <span className="detail-value">{manager.empId}</span>
            </div>
            <div className="detail">
                <h4><b>First Name:</b></h4>
                <span className="detail-value">{manager.firstName}</span>
            </div>
            <div className="detail">
                <h4><b>Last Name:</b></h4>
                <span className="detail-value">{manager.lastName}</span>
            </div>
            <div className="detail">
                <h4><b>Email Address:</b></h4>
                <span className="detail-value">{manager.userName}</span>
            </div>
            <div className="detail">
                <h4><b>City:</b></h4>
                <span className="detail-value">{manager.city}</span>
            </div>
            <div className="detail">
                <h4><b>State:</b></h4>
                <span className="detail-value">{manager.state}</span>
            </div>
            <div className="detail">
                <h4><b>Phone Number:</b></h4>
                <span className="detail-value">{manager.phone_no}</span>
            </div>
        </div>
        <div className="manager-section">
            <h3>Company Specific Details</h3>
            <div className="detail">
                <h4><b>Domain Name:</b></h4>
                <span className="detail-value">{manager.domain}</span>
            </div>
            <div className="detail">
                <h4><b>Role:</b></h4>
                <span className="detail-value">Manager</span>
            </div>
    </div>
</div>

            <div className="btn-container">
                <Link to={`/manager/reps/${manager.empId}`}><button className="btn-update">View Representatives</button></Link>
                <Link to={`/manager/update/${manager.empId}`}><button className="btn-update">Update</button></Link>
                <button className="btn-delete" onClick={openModal}>Delete</button>
            </div>
            <Modal show={showModal} onClose={closeModal} onConfirm={confirmDelete} employee={'Manager'} />
        </div>
    );
}

export default Manager;
