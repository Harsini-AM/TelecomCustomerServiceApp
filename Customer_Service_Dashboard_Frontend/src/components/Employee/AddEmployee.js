import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import { Link, useNavigate, useParams } from 'react-router-dom';
import Loading from '../General/Loading';
import { validateForm } from './FormValidation';
import { cityToStateMap } from '../General/constants';
import { sendEmail } from '../General/ServiceFunctions';
import './AddEmployee.css'


function AddEmployee({ type, role }) {
    console.log(type)
    //const EMPLOYEE_URL = "http://localhost:8086/employee";
    const [ details,setDetails] = useState({});
    const [isLoading, setIsLoading] = useState(true);
    const { id } = useParams();
    const navigate = useNavigate();
    const [errors, setErrors] = useState({});
    const [managers, setManagers] = useState([]);
    const [selectedManager] = useState(null);
    

    const [data, setData] = useState({
        fName: '',
        lName: '',
        email: '',
        phone_no: '',
        city: '',
        state: '',
        currentRole: role ? role : '',
        updatedRole: role ? role:'',
        domain: '',
        managerId: '',
        repId: null,
        userName: '',
        password: '',
        PasswordChanged: false,
    });
    const isReadOnly = (type === 'update');

    useEffect(() => {
        const fetchData = async () => {
            try {
                if(type==='update'){
                if (id && role === 'manager') {
                    const response = await axios.get(`${process.env.REACT_APP_EMPLOYEE_GET_URL}/getManager/${id}`);
                    setDetails(response.data);
                    setData({
                        fName: response.data.firstName,
                        lName: response.data.lastName,
                        email: response.data.userName,
                        phone_no: response.data.phone_no,
                        city: response.data.city,
                        state: response.data.state,
                        currentRole: role ?? '',
                        updatedRole: role ? role:'',
                        domain: response.data.domain,
                        userName: response.data.userName,
                        password: response.data.password
                    });
                }

                if (id && role === 'representative') {
                    const response = await axios.get(`${process.env.REACT_APP_EMPLOYEE_GET_URL}/getRepresentative/${id}`);
                    setDetails(response.data);
                    setData({
                        fName: response.data.fName,
                        lName: response.data.lName,
                        email: response.data.userName,
                        phone_no: response.data.phone_no,
                        city: response.data.city,
                        state: response.data.state,
                        currentRole: role,
                        updatedRole: role ? role:'',
                        domain: response.data.domain,
                        managerId: response.data.managerId ?? '',
                        repId: response.data.empId,
                        userName: response.data.userName,
                        password: response.data.password
                    });
                }
            }
            } catch (err) {
                console.error(err);
            } finally {
                setIsLoading(false);
            }
        };

        fetchData();
    }, [id, role, type]);


    useEffect(() => {
        const fetchManagers = async () => {
            try {
                const response = await axios.get(`${process.env.REACT_APP_EMPLOYEE_GET_URL}/getManagers`);
                setManagers(response.data);
                
            } catch (error) {
                console.error('Error fetching managers:', error);
            }
        };
        
        fetchManagers();
    }, []);

    const dataHandler = (e) => {
        const name = e.target.name;
        const value = e.target.value;
        setData(data => {
            const updatedData = { ...data, [name]: value };
            if (name === 'city') {
                updatedData.state = cityToStateMap[value] || '';
            }
            return updatedData;
        });
    };

    const onSubmitHandler = async (e) => {
        e.preventDefault();
        const newData = { ...data, userName: data.email, password: data.password, NumberOfTickets:0 };
        const formErrors = validateForm(newData);
        setErrors(formErrors);

        if (Object.keys(formErrors).length === 0) {
            try {
               
                if(data.updatedRole ==='manager'){
                const response = await axios.post(`${process.env.REACT_APP_EMPLOYEE_GET_URL}/addManager`, newData);
                const data1 = response.data.body;
                if (data1.password && data1.userName) {
                    const { userName, password } = data1;
                    sendEmail(userName, password);
                } else {
                    console.error("Failed to send mail!");
                }
                if (response.data) {
                    toast.success("Successfully added!");
                    navigate('/admin/list');
                } 
            }
                else{
                    const response = await axios.post(`${process.env.REACT_APP_EMPLOYEE_GET_URL}/addRepresentative`, newData);
                    const data1 = response.data;
                    if (data1.password && data1.userName) {
                        const { userName, password } = data1;
                        sendEmail(userName, password);
                       
                    } else {
                        console.error("Failed to send mail!");
                    }
                
    
                    if (response.data) {
                        toast.success("Successfully added!");
                        navigate('/admin/list');
                    } 
                }
                
            } catch (error) {
                if (error?.response?.status===409) {
                   
                    toast.error('Duplicate entry: ' + error.response.data);
                } else if(error?.response?.status===404){
                    toast.error('Existing Manager Id is required!');
                }
                else {
                    toast.error('An error occurred: ' + error?.message);
                }
            toast.error(error.response);
            }
        
        } else {
            setErrors(formErrors);
        }
    };

    const handleManagerChange = (e) => {
        const selectedManagerId = e.target.value;
        console.log(selectedManagerId)
        const selectedManager = managers.find(manager => manager.empId === parseInt(selectedManagerId));
        setData(data => ({ ...data, domain: selectedManager.domain, managerId:selectedManagerId}))
    }
       
    
    const handleErrors = (error) => {
        console.error(error);
        if (error?.response?.status === 409) {
            toast.error('Duplicate entry: ' + error.response.data);
        } else if (error?.response?.status === 404) {
            toast.error('Existing Manager Id is required!');
        } else {
            toast.error('An error occurred: ' + error?.response?.data);
        }
    };
    
    const onUpdateHandler = async (e) => {
        e.preventDefault();
        console.log(data);
    
        let newData = { 
            ...data, 
            empId: data.managerId, 
            userName: data.email,
            password: data.password, 
            updatedRole: data.updatedRole 
        };
    
        const formErrors = validateForm(newData);
        setErrors(formErrors);
    
        if (Object.keys(formErrors).length === 0) {
            try {
                let response;
                if (data.currentRole === 'manager' && data.updatedRole === 'representative') {
                    response = await axios.put(`${process.env.REACT_APP_EMPLOYEE_GET_URL}/depromoteEmployee/${id}`, newData);
                    toast.success("Successfully depromoted!");
                } else if (data.currentRole === 'representative' && data.updatedRole === 'manager') {
                    response = await axios.put(`${process.env.REACT_APP_EMPLOYEE_GET_URL}/promoteEmployee/${id}`, newData);
                    toast.success("Successfully promoted!");
                } else if (data.currentRole === 'manager' && data.updatedRole === 'manager') {
                    response = await axios.put(`${process.env.REACT_APP_EMPLOYEE_GET_URL}/updateManager/${id}`, newData);
                    toast.success("Successfully Updated!");
                } else {
                    response = await axios.put(`${process.env.REACT_APP_EMPLOYEE_GET_URL}/updateRepresentative/${id}`, newData);
                    toast.success("Successfully Updated!");
                }
                if (response.data) {
                    navigate('/admin/list');
                }
            } catch (error) {
                handleErrors(error);
            }
        } else {
            setErrors(formErrors);
        }
    };
    
    
    if (isLoading) {
        return <Loading />;
    }

    return (
        <div className="form-container">
            <h2>Add/Update Employee</h2>
            <form onSubmit={type === 'add' ? onSubmitHandler : onUpdateHandler}>
                <div>
                    <p>First Name <span className="required">*</span></p>
                    <input onChange={dataHandler} value={data.fName} type='text' name='fName' placeholder='First Name' required />
                    {errors.fName && <p className="error-message">{errors.fName}</p>}
                </div>

                <div >
                    <p>Last Name <span className="required">*</span></p>
                    <input onChange={dataHandler} value={data.lName} type='text' name='lName' placeholder='Last Name' required />
                    {errors.lName && <p className="error-message">{errors.lName}</p>}
                </div>

                <div >
                    <p>Email Address <span className="required">*</span></p>
                    <input onChange={dataHandler} value={data.email} type='email' name='email' placeholder='Email address' required readOnly={isReadOnly} className={isReadOnly ? 'read-only' : ''} />
                    {errors.email && <p className="error-message">{errors.email}</p>}
                </div>

                <div>
                    <p>Phone Number <span className="required">*</span></p>
                    <div className="phone-input-container">
                        <span className="country-code">+91</span>
                        <input onChange={dataHandler} value={data.phone_no==='' ? '':Number(data.phone_no)} type='text' name='phone_no' required />
                    </div>
                    {errors.phone_no && <p className="error-message">{errors.phone_no}</p>}
                </div>

                <div >
                    <p>City <span className="required">*</span></p>
                    <select onChange={dataHandler} value={data.city} name='city' required>
                        <option value="" disabled>Select a city</option>
                        <option value="chennai">Chennai</option>
                        <option value="bangalore">Bangalore</option>
                        <option value="hyderabad">Hyderabad</option>
                        <option value="mumbai">Mumbai</option>
                        <option value="kolkata">Kolkata</option>
                        
                    </select>
                    {errors.city && <p className="error-message">{errors.city}</p>}
                </div>

                <div>
                    <p>State <span className="required">*</span></p>
                    <select onChange={dataHandler} value={data.state} name='state' required>
                        <option value="" disabled>Select a state</option>
                        <option value="Tamil Nadu">Tamil Nadu</option>
                        <option value="Karnataka">Karnataka</option>
                        <option value="Telangana">Telangana</option>
                        <option value="Maharashtra">Maharashtra</option>
                        <option value="West Bengal">West Bengal</option>
                    </select>
                </div>

                <div>
                        <p>Employee Role <span className="required">*</span></p>
                        <select name='updatedRole' onChange={(e) => setData(data => ({ ...data, updatedRole: e.target.value }))} value={data.updatedRole} required>
                            <option disabled value="">Select role</option>
                            <option value='manager'>Manager</option>
                            <option value='representative'>Representative</option>
                        </select>
                        {errors.updatedRole && <p className="error-message">{errors.updatedRole}</p>}
                </div>

                 <div  className='manager-domain'>
                    {data.updatedRole === 'representative' && (
                    <div>
                        <p>Manager Id</p>
                        <select onChange={handleManagerChange} value={data.managerId} name='managerId'>
                            <option value={(data.updatedRole=='representative') ? '':selectedManager} disabled selected>Select Manager</option>
                            {managers.map(manager => (
                                <option key={manager.empId} value={manager.empId}>
                                    {manager.fName} {manager.lName}- {manager.domain}
                                </option>
                            ))}
                        </select>
                    {errors.managerId && <p className="error-message">{errors.managerId}</p>}
                    </div>
                 )}
                </div>

                <div>
                    <p>Domain <span className="required">*</span></p>
                        <select name='domain' onChange={(type === 'update' && role === 'manager') ? (e) => setData(data => ({ ...data, domain: data.domain })) : (e) => setData(data => ({ ...data, domain: e.target.value }))} value={data.domain} required>
                            <option disabled value="">Select domain</option>
                            <option value='network'>Network Management</option>
                            <option value='billing'>Billing Management</option>
                            <option value='technical'>Technical Support</option>
                            <option value='outage'>Outage Management</option>
                            <option value='plans'>Plan Management</option>
                        </select>
                    {errors.domain && <p className="error-message">{errors.domain}</p>}
                </div>
        
                <button type='submit' className='add-btn'>{type === 'add' ? "Add " : "Update "} Employee Details</button>
            </form>
            <Link to='/admin/list'><button className="back-btn">Back to list</button></Link>
        </div>
    );
}

export default AddEmployee;





