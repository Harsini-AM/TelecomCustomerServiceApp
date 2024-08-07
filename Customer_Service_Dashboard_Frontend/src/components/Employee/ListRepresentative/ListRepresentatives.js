import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { deleteRepresentative } from '../../General/ServiceFunctions';
import '../ListManager/ListManager.css'
import Modal from '../../General/Modal'; // Import the Modal component here
import { Pagination, PaginationItem, PaginationLink } from 'reactstrap';
 
function ListRepresentatives() {
  const [representatives, setRepresentatives] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedRepresentativeId, setSelectedRepresentativeId] = useState(null);
  const [pagination, setPagination] = useState({
    totalElements: 0,
    totalPages: 0,
    pageSize:5,
    pageNumber: 0,
    lastPageIndex: 0,
});

useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`${process.env.REACT_APP_EMPLOYEE_GET_URL}/getRepCount`);
        setPagination((pagination) => {
            const totalElements = response.data;
            const totalPages = Math.ceil(totalElements / pagination.pageSize);
            return {
              ...pagination,
              totalElements,
              totalPages,
              lastPageIndex: totalPages - 1,
            };
          });
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, []);

useEffect(() => {
    loadAllPosts(0, pagination.pageSize).then(data => {
        setRepresentatives(data);
    }).catch(err => {
        console.log(err);
    });
}, [pagination.pageSize]);

const loadAllPosts = async (pageNumber, pageSize) => {
    return axios
      .get(
        `${process.env.REACT_APP_EMPLOYEE_GET_URL}/paginationRepresentative/${pageNumber}/${pageSize}`
      )
      .then((response) => response.data);
};

const changePage = (pageNumber) => {
    if (pageNumber < 0 || pageNumber > pagination.lastPageIndex) {
        return;
    }
    loadAllPosts(pageNumber, pagination.pageSize).then(data => {
        setRepresentatives(data);
        setPagination((pagination) => ({
            ...pagination,
            pageNumber: pageNumber,
        }));
    }).catch(error => {
        console.log(error);
    });
};


  const openModal = (repId) => {
    setSelectedRepresentativeId(repId);
    setIsModalOpen(true);
  };
 
  const closeModal = () => {
    setIsModalOpen(false);
    setSelectedRepresentativeId(null);
  };
 
  const handleDelete = async () => {
    if (selectedRepresentativeId) {
       deleteRepresentative(selectedRepresentativeId);
      setRepresentatives(representatives.filter(rep => rep?.empId !== selectedRepresentativeId));
      closeModal();
    }
  };
 

 
 
  return (
    <div className="representative-container">
      <h3 className='bold'>List of Representatives</h3>
      <table className="manager-table">
        <thead>
          <tr>
            <th>RepId</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email Address</th>
            <th>Domain Name</th>
            <th>Manager Id</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {
          representatives?.map(representative => (
            <tr key={representative?.empId}>
              <td>{representative?.empId}</td>
              <td>{representative?.fName}</td>
              <td>{representative?.lName}</td>
              <td>{representative?.userName}</td>
              <td>{representative?.domain?.charAt(0).toUpperCase() + representative?.domain?.slice(1)}</td>
              <td>{representative?.managerId}</td>
              
              <td className="manager-actions">
                <Link to={`/representative/${representative?.empId}`}><button>View Details</button></Link>
                <Link to={`/representative/update/${representative?.empId}`}><button>Update</button></Link>
                <button onClick={() => openModal(representative?.empId)} className='btn-delete'>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
 
      <div className='my-pagination'>
            <Pagination size='md' bg-dark>
                <PaginationItem onClick={() => changePage(pagination.pageNumber - 1)} disabled={pagination.pageNumber === 0}>
                    <PaginationLink previous />
                </PaginationItem>

                {[...Array(pagination.totalPages)].map((item, index) => (
                    <PaginationItem onClick={() => changePage(index)} key={index} active={pagination.pageNumber === index}>
                        <PaginationLink>
                            {index + 1}
                        </PaginationLink>
                    </PaginationItem>
                ))}

                <PaginationItem onClick={() => changePage(pagination.pageNumber + 1)} disabled={pagination.pageNumber === pagination.lastPageIndex}>
                    <PaginationLink next />
                </PaginationItem>
            </Pagination>
            </div>
 
      <Modal
        show={isModalOpen}
        onClose={closeModal}
        onConfirm={handleDelete}
        employee={'Representative'}
      />
    </div>
  );
}
 
export default ListRepresentatives;
 