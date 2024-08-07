import React from 'react'
import ChatBot from 'react-simple-chatbot';
import { ThemeProvider } from 'styled-components';

function Chatbot() {

    const steps = [
        {
            id: '0',
            message: 'Hello!!',
            trigger: '1',
        },
        {
            id: '1',
            message: 'Please write your username',
            trigger: '2',
        },
        {
            id: '2',
            user: true,
            trigger: '3',
        },
        {
            id: '3',
            message: 'Hi {previousValue}, how can I help you?',
            trigger: '4',
        },
        {
            id: '4',
            options: [
                { value: 'network', label: 'Network Issues', trigger: 'network' },
                { value: 'billing', label: 'Billing Issues', trigger: 'billing' },
                { value: 'planManagement', label: 'Plan Management', trigger: 'planManagement' },
                { value: 'outage', label: 'Outage', trigger: 'outage' },
                { value: 'technical', label: 'Technical', trigger: 'technical' },
                { value: 'other', label: 'Other', trigger: 'other' },
            ],
        },
        // Domain-specific queries...
        {
            id: 'network',
            message: 'Please describe your network issue.',
            trigger: 'user-network-query',
        },
        {
            id: 'billing',
            message: 'Please describe your billing issue.',
            trigger: 'user-billing-query',
        },
        {
            id: 'planManagement',
            message: 'Please describe your plan management issue.',
            trigger: 'user-plan-management-query',
        },
        {
            id: 'outage',
            message: 'Please describe your outage issue.',
            trigger: 'user-outage-query',
        },
        {
            id: 'technical',
            message: 'Please describe your technical issue.',
            trigger: 'user-technical-query',
        },
        {
            id: 'other',
            message: 'Please describe your issue.',
            trigger: 'user-other-query',
        },
        // Responses...
        {
            id: 'user-network-query',
            user: true,
            trigger: 'network-response',
        },
        {
            id: 'user-billing-query',
            user: true,
            trigger: 'billing-response',
        },
        {
            id: 'user-outage-query',
            user: true,
            trigger: 'outage-response',
        },
        {
            id: 'user-plan-management-query',
            user: true,
            trigger: 'plan-management-response',
        },
        {
            id: 'user-technical-query',
            user: true,
            trigger: 'technical-response',
        },
        {
            id: 'user-other-query',
            user: true,
            trigger: 'other-response',
        },
        {
            id: 'network-response',
            message: 'It seems you are having network issues. Please restart your router. If the problem persists, contact our support team.',
            trigger: 'query-again',
        },
        {
            id: 'billing-response',
            message: 'For billing issues, please check your latest invoice in the billing section or contact our billing support team.',
            trigger: 'query-again',
        },
        {
            id: 'outage-response',
            message: 'We are experiencing an outage in your area. Our team is working on it and services will be restored shortly.',
            trigger: 'query-again',
        },
        {
            id: 'technical-response',
            message: 'For technical issues, please try restarting your device. If the problem persists, contact our technical support team.',
            trigger: 'query-again',
        },
        {
            id: 'plan-management-response',
            message: 'For plan management issues, please visit the plan management section in your account or contact our support team for assistance.',
            trigger: 'query-again',
        },
        {
            id: 'other-response',
            message: 'Thank you for your query. How else can I assist you?',
            trigger: 'query-again',
        },
        {
            id: 'query-again',
            message: 'Do you have another question or issue?',
            trigger: 'yes-no-options',
        },
        {
            id: 'yes-no-options',
            options: [
                { value: 'yes', label: 'Yes', trigger: 'yes-response' },
                { value: 'no', label: 'No', trigger: 'no-response' },
            ],
        },
        {
            id: 'yes-response',
            message: 'Please choose the domain of your query.',
            trigger: '4',
        },
        {
            id: 'no-response',
            message: 'Thank you for getting back to us whenever you have a query.',
            trigger: 'wait',
        },
        {
            id:"wait",
            user: true,
            trigger: '4',
        }
    ];

    const theme = {
        background: 'white ',
        headerBgColor: '#000',
        headerFontSize: '20px',
        botBubbleColor: '#f5f5f5',
        headerFontColor: 'white',
        botFontColor: 'black',
        userBubbleColor: '#000',
        userFontColor: 'white',
    };

    const config = {
        floating: true,
    };
  return (
    <div>
      
            <ThemeProvider theme={theme}>
                <ChatBot
                    headerTitle="Customer Support Bot"
                    steps={steps}
                    {...config}
                />
            </ThemeProvider>
       
    </div>
  )
}

export default Chatbot
