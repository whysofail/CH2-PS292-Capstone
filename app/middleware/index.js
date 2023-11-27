const {authorize, isAdmin} = require('./authorization')
const { emailExist } = require('./emailCheck')

module.exports = {
    authorize,
    emailExist
}
