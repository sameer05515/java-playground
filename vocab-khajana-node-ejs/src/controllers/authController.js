const express = require('express');
module.exports = function authController() {
  const router = express.Router();
  router.get('/login', (req, res) => res.render('login', { errorMessage: '', allUsers: [] }));
  router.post('/login', express.urlencoded({ extended: true }), (req, res) => {
    const { name, password } = req.body;
    if (name === 'jbk' && password === 'jbk') return res.render('welcome', { name });
    res.status(401).render('login', { errorMessage: 'Invalid username/password', allUsers: [] });
  });
  router.get('/welcome', (req, res) => res.render('welcome', { name: req.query.name || 'Guest' }));
  router.get('/list-todos', (req, res) => res.render('list-todos', {
    name: req.query.name || 'Guest',
    todos: ['Study vocabulary', 'Review Java', 'Practice Spring MVC']
  }));
  return router;
};
