
export const validateForm = (data) => {
    let errors = {};

    
    if (!data.fName) {
        errors.fName = "First Name is required";
    }

   
    if (!data.lName) {
        errors.lName = "Last Name is required";
    }

    if (!data.email) {
        errors.email = "Email is required";
    } else if (!data.email.endsWith('@LIT.com')) {
        errors.email = "Email must end with '@LIT.com'";
    }


    if (!data.phone_no) {
        errors.phone_no = "Phone number is required";
    } else if (!/^[6-9]\d{9}$/.test(data.phone_no)) {
        errors.phone_no = "Phone number must be 10 digits and should start with 6,7,8, or 9.";
    }


    if (!data.city) {
        errors.city = "City is required";
    }

  
    if (!data.updatedRole) {
        errors.updatedRole = "Role is required";
    }

  
    if (!data.domain) {
        errors.domain = "Domain is required";
    }

    if (data.updatedRole === 'representative' && (!data.managerId || data.managerId <= 0)) {
        errors.managerId = "Manager ID must be greater than zero";
    }

    return errors;
};
