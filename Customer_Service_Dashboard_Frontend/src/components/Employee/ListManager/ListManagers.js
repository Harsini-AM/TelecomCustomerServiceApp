import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { handleDelete } from '../../General/ServiceFunctions';
import Loading from '../../General/Loading';
import './ListManager.css'; 
import Modal from '../../General/Modal';
import { Pagination, PaginationItem, PaginationLink} from 'reactstrap';

function ListManagers() {
    const [managers, setManagers] = useState([]);
    const [selectedManager, setSelectedManager] = useState(null)
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [pagination, setPagination] = useState({
        totalElements: 0,
        totalPages: 0,
        pageSize: 5,
        pageNumber: 0,
        lastPageIndex: 0,
    });

    useEffect(() => {
        const fetchData = async () => {
          try {
            const response = await axios.get(`${process.env.REACT_APP_EMPLOYEE_GET_URL}/getManagerCount`);
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
            setManagers(data);
        }).catch(err => {
            console.log(err);
        });
    }, [pagination.pageSize]);

    const loadAllPosts = async (pageNumber, pageSize) => {
        return axios
          .get(
            `${process.env.REACT_APP_EMPLOYEE_GET_URL}/paginationManager/${pageNumber}/${pageSize}`
          )
          .then((response) => response.data);
    };

    const changePage = (pageNumber) => {
        if (pageNumber < 0 || pageNumber > pagination.lastPageIndex) {
            return;
        }

        loadAllPosts(pageNumber, pagination.pageSize).then(data => {
            setManagers(data);
            setPagination((pagination) => ({
                ...pagination,
                pageNumber: pageNumber,
            }));
        }).catch(error => {
            console.log(error);
        });
    };

    const fetchManagerData = async () => {
        try {
            const response = await axios.get(`${process.env.REACT_APP_EMPLOYEE_GET_URL}/getManagers`);
            setManagers(response.data);
        } catch (err) {
            console.error(err);
        } 
    };

    const openModal = (empId) => {
        setSelectedManager(empId);
        setIsModalOpen(true);
    };
    
    const closeModal = () => {
        setIsModalOpen(false);
        setSelectedManager(null);
    };
    
    const handleDeleteModal = async () => {
        if (selectedManager) {
            await handleDelete(selectedManager);
            //fetchManagerData();
            closeModal();
        }
    };

    return (
        <div className="list-managers-container">
            <h3>List of Managers</h3>
            <table className="manager-table">
                <thead>
                    <tr>
                        <th>Employee ID</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email Address</th>
                        <th>Domain</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {managers.map(manager => (
                        <tr key={manager?.empId}>
                            <td>{manager?.empId}</td>
                            <td>{manager?.firstName}</td>
                            <td>{manager?.lastName}</td>
                            <td>{manager?.userName}</td>
                            <td>{manager?.domain?.charAt(0).toUpperCase() + manager?.domain?.slice(1)}</td>
                            <td className="manager-actions" >
                                <Link to={`/manager/${manager?.empId}`}><button>View Details</button></Link>
                                <Link to={`/manager/update/${manager?.empId}`}><button>Update</button></Link>
                                <button onClick={() => openModal(manager?.empId)} className='btn-delete'>Delete</button>
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
                onConfirm={handleDeleteModal}
                employee={'Manager'}
            />
        </div>
    );
}

export default ListManagers;
