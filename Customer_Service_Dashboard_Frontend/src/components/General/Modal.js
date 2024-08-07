import React from 'react';
import './Modal.css'

const Modal = ({ show, onClose, onConfirm, employee }) => {
    if (!show) {
        return null;
    }

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <h4>Are you sure you want to delete this {employee}?</h4>
                <button className="btn-confirm" onClick={onConfirm}>Yes, Delete</button>
                <button className="btn-cancel" onClick={onClose}>Cancel</button>
            </div>
        </div>
    );
};

export default Modal;
