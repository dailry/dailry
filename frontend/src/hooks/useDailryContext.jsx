import PropTypes from 'prop-types';
import { createContext, useContext, useState } from 'react';

const DailryContext = createContext();

export const DailryProvider = (props) => {
  const { children } = props;
  const [currentDailry, setCurrentDailry] = useState({
    id: null,
    pages: [],
  });

  const [currentDailryPage, setCurrentDailryPage] = useState(0);

  return (
    <DailryContext.Provider
      value={{
        currentDailry,
        setCurrentDailry,
        currentDailryPage,
        setCurrentDailryPage,
      }}
    >
      {children}
    </DailryContext.Provider>
  );
};

DailryProvider.propTypes = {
  children: PropTypes.node,
};

export const useDailryContext = () => {
  return useContext(DailryContext);
};
