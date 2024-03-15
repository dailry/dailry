import PropTypes from 'prop-types';
import { createContext, useContext, useState } from 'react';

const ModalContext = createContext();

export const ModalProvider = (props) => {
  const { children } = props;
  const [modalType, setModalType] = useState(null);

  const closeModal = () => {
    setModalType(null);
  };

  return (
    <ModalContext.Provider value={{ modalType, setModalType, closeModal }}>
      {children}
    </ModalContext.Provider>
  );
};

export const useModalContext = () => {
  return useContext(ModalContext);
};

ModalProvider.propTypes = {
  children: PropTypes.node,
};
