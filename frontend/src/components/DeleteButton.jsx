
import { Button } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTimes } from '@fortawesome/free-solid-svg-icons';

// eslint-disable-next-line react/prop-types
const DeleteButton = ({ onClick }) => {
    return (
        <Button className='mt-2' variant="danger" onClick={onClick}>
            <FontAwesomeIcon icon={faTimes} />
        </Button>
    );
};

export default DeleteButton;
