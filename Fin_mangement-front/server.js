// server.js
const express = require('express');
const axios = require('axios');

const app = express();
const PORT = 3000;


// server.js (일부 추가)
app.use(express.static('public'));


// Spring Boot의 사용자 API 호출
app.get('/users', async (req, res) => {
    try {
        const response = await axios.get('http://localhost:8080/api/users');
        res.json(response.data);
    } catch (error) {
        console.error('Error fetching users:', error);
        res.status(500).send('Error fetching users');
    }
});

// Spring Boot의 거래 내역 API 호출
app.get('/transactions', async (req, res) => {
    try {
        const response = await axios.get('http://localhost:8080/api/transactions');
        res.json(response.data);
    } catch (error) {
        console.error('Error fetching transactions:', error);
        res.status(500).send('Error fetching transactions');
    }
});



// 서버 실행
app.listen(PORT, () => {
    console.log(`Node.js server running on port ${PORT}`);
});
