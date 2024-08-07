import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';
 
const Feedback = () => {
  const { ticketId } = useParams(); // Accessing the customerId from the URL params
  const [rating, setRating] = useState(null);
  const [feedback, setFeedback] = useState('');
  const [isSubmitted, setIsSubmitted] = useState(false); // State to track if feedback is submitted
  const navigate = useNavigate();
 
  const handleRatingChange = (value) => {
    setRating(value);
  };
 
  const handleFeedbackChange = (event) => {
    setFeedback(event.target.value);
  };
 
  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const authData = sessionStorage.getItem('auth');
      if(authData) {
        const parsedAuthData = JSON.parse(authData);
        console.log(parsedAuthData)
        const cusId = parsedAuthData?.id; // Logged-in user ID
        const response = await axios.post('http://localhost:8087/customer/addFeedback', {
          description: feedback,
          rating: rating,
          customer_id: cusId, // Using the logged-in user ID as the customerId
          ticketId: ticketId
        });
        console.log('Feedback submitted:', response.data);
        setIsSubmitted(true);
        navigate('/customer/tickets');
      }
    } catch (error) {
      console.error('Error submitting feedback:', error);
    }
  };
 
  return (
    <>
      <div style={{ maxWidth: '400px', margin: '180px auto', padding: '20px', border: '1px solid #ccc', borderRadius: '5px' }}>
        <h2>Feedback Form</h2>
        <form onSubmit={handleSubmit}>
          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
            {[...Array(5)].map((_, index) => {
              const ratingValue = index + 1;
              return (
                <label key={ratingValue} style={{ cursor: 'pointer', fontSize: '30px', color: '#ddd' }}>
                  <input
                    type="radio"
                    name="rating"
                    value={ratingValue}
                    onClick={() => handleRatingChange(ratingValue)}
                    style={{ display: 'none' }}
                  />
                  <span style={{ color: ratingValue <= rating ? '#ffd700' : '#ddd' }}>&#9733;</span>
                </label>
              );
            })}
          </div>
          <textarea
            name="feedback"
            placeholder="Enter your feedback here..."
            value={feedback}
            onChange={handleFeedbackChange}
            style={{ width: '100%', height: '100px', marginTop: '10px', padding: '5px', resize: 'vertical' }}
            required
          ></textarea>
          {!isSubmitted && ( // Render the button only if feedback is not submitted
            <button type="submit" style={{ marginTop: '10px', padding: '10px 20px', backgroundColor: '#007bff', color: '#fff', border: 'none', borderRadius: '5px', cursor: 'pointer' }}>Submit Feedback</button>
          )}
          {isSubmitted && ( // Render a message after feedback is submitted
            <p style={{ marginTop: '10px', color: '#007bff', textAlign: 'center' }}>Thank you for your feedback!</p>
          )}
        </form>
      </div>
    </>
  );
};
 
export default Feedback;
 