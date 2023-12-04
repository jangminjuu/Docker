const express = require('express');

const PORT = process.env.PORT || 8080;
const HOST = '0.0.0.0'; 

//APP
const app = express();
app.get('/', (req,res) => {
    res.send("안녕하세요!!")
});

app.listen(PORT, HOST, () => {
    console.log(`Server is running on ${HOST}:${PORT}`);
  });
