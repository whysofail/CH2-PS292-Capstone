const express = require('express');
const router = express.Router();


router.get('/halo', (req, res) => {
    return res.json('Halo')
})


module.exports = router