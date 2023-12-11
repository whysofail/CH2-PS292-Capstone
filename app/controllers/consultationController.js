const { User, Lawyer, Consultation} = require('../../models')


const getAllConsultation = async (req, res) => {

}

const getConsultation = async (req, res) => {

}

const makeConsultation = async (req, res) => {
    const user = req.user
    const { lawyer_id } = req.query

    try {
        const lawyer = await Lawyer.findOne({ id: lawyer_id})
        if(lawyer.length = 0){
            return res.status(400).json({msg: `No lawyer found with id : ${lawyer_id}`})
        }

    } catch (error) {
        
    }
}

