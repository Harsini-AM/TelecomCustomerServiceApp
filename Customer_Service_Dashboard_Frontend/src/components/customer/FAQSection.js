import React, { useState, useEffect } from 'react';
import './FAQSection.css'; // Import the CSS file
import axios from 'axios'; // Import axios for making API requests

const FAQSection = () => {
  const [expandedIndex, setExpandedIndex] = useState(null);
  const [selectedDomain, setSelectedDomain] = useState('');
  const [faqs, setFaqs] = useState([]);
 
  useEffect(() => {
    if (selectedDomain) {
      axios.get(`http://localhost:8088/customer/faqs`)
        .then(response => {
          const data = response.data;
          console.log(data);
          const data1=data.map(q=> q.domain===selectedDomain  && q);
          const data2 = data1.filter(value=> value!==false);
          console.log(data2);
          setFaqs(data2);
        })
        .catch(error => {
          console.error('Error fetching FAQs:', error);
        });
    } else {
      axios.get("http://localhost:8088/customer/faqs")
        .then(response => {
          const data = response.data;
          // Select random 6 FAQs fro8m the response data
          const randomFaqs = data.sort(() => {
            const crypto = window.crypto || window.msCrypto;
            const array = new Uint32Array(1);
            crypto.getRandomValues(array);
            return array[0] - 0.5;
          }).slice(0, 5);
 
          setFaqs(randomFaqs);
        })
        .catch(error => {
          console.error('Error fetching FAQs:', error);
        });
    }
  }, [selectedDomain]);
 
  const toggleFAQ = (index) => {
    if (expandedIndex === index) {
      setExpandedIndex(null);
    } else {
      setExpandedIndex(index);
    }
  };
 
  return (
    <div className="faq-container">
      <div className='faq-heading'><h2>Got queries?<br />Check FAQs for answers</h2></div>
      <div className='domain-dropdown'>
        <select value={selectedDomain} onChange={(e) => setSelectedDomain(e.target.value)}>
          <option value="">Select Domain</option>
          <option value="network">Network</option>
          <option value="billing">Billing</option>
          <option value="technical">Technical Support</option>
          <option value="outage">Outage</option>
          <option value="plan">Plan Management</option>
        </select>
      </div>
      <div className="row">
        {faqs.map((faq, index) => (
          <div key={faq.id} className="faq-item">
            <div className="faq-question" >
              {faq.question}
              <button onClick={() => toggleFAQ(index)}>{expandedIndex === index ? '-' : '+'}</button>
            </div>
            {expandedIndex === index && (
              <div className="faq-answer">
                {faq.answer}
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
};
 
export default FAQSection;