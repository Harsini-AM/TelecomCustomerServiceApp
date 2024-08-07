import axios from 'axios';
import React, { useEffect, useState } from 'react'
import Loading from '../General/Loading';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import { deleteRepresentative, handleDelete } from '../General/ServiceFunctions';
import Modal from '../General/Modal';
import '../Manager/ManagerDetails/Manager.css'

function Representative() {
  let { repId } = useParams();
  const navigate = useNavigate()
  repId = Number(repId)
    const [isLoading, setIsLoading]= useState(true);
    const [representative, setRepresentative]= useState({});
    const [showModal, setShowModal] = useState(false);

    useEffect(() => {
        // Function to fetch data
        const fetchRepresentativeData = async () => {
          try {
            const response = await axios.get(`${process.env.REACT_APP_EMPLOYEE_GET_URL}/getRepresentative/${repId}`);
            setRepresentative(response.data);
            console.log(response.data)
          } catch (err) {
            console.error(err);
            toast.error(err?.response?.data)
          } finally {
            setIsLoading(false);
          }
        };
    
        // Call the function to fetch data
        fetchRepresentativeData();
      }, []);

    const openModal = () => {
        setShowModal(true);
    };

    const closeModal = () => {
        setShowModal(false);
    };

    const confirmDelete = () => {
      deleteRepresentative(representative.empId);
        closeModal();
        navigate("/admin/list")
    };
     

      if (isLoading) {
        return <Loading />;
      }
  return (
    <>

     <div className="manager-container">
            <h2>Representative Details</h2>
            <div className="manager-details">
        <div className="manager-section">
            <h3>Representative Basic Details</h3>
            <div className="detail">
                <h4><b>First Name:</b></h4>
                <span className="detail-value">{representative.fName}</span>
            </div>
        <div className="detail">
            <h4><b>Last Name:</b></h4>
            <span className="detail-value">{representative.lName}</span>
        </div>
        <div className="detail">
            <h4><b>Email Address:</b></h4>
            <span className="detail-value">{representative.userName}</span>
        </div>
        <div className="detail">
            <h4><b>City:</b></h4>
            <span className="detail-value">{representative.city}</span>
        </div>
        <div className="detail">
            <h4><b>State:</b></h4>
            <span className="detail-value">{representative.state}</span>
        </div>
        <div className="detail">
            <h4><b>Phone Number:</b></h4>
            <span className="detail-value">{representative.phone_no}</span>
        </div>
    </div>
    <div className="manager-section">
        <h3>Company Specific Details</h3>
        <div className="detail">
            <h4><b>Domain Name:</b></h4>
            <span className="detail-value">{representative.domain}</span>
        </div>
        <div className="detail">
            <h4><b>Role:</b></h4>
            <span className="detail-value">Manager</span>
        </div>
    </div>
</div>

            <div className="btn-container">
                
                <Link to={`/representative/update/${representative?.empId}`}><button className="btn-update">Update</button></Link>
                <button className="btn-delete" onClick={openModal}>Delete</button>
            </div>
            <Modal show={showModal} onClose={closeModal} onConfirm={confirmDelete} employee={'Manager'} />
        </div>
    <Modal show={showModal} onClose={closeModal} onConfirm={confirmDelete} employee={'Manager'} />
    </>
  )
}

export default Representative
