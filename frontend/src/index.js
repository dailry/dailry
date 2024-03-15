import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import GlobalStyles from './styles/GlobalStyle';
import App from './App';
// import { worker } from './mocks/worker';
import { DailryProvider } from './hooks/useDailryContext';
import { ModalProvider } from './hooks/useModalContext';

// if (process.env.NODE_ENV === 'development') {
//   await worker.start();
// }

const root = createRoot(document.getElementById('root'));
root.render(
  <StrictMode>
    <GlobalStyles />
    <ModalProvider>
      <DailryProvider>
        <BrowserRouter>
          <App />
          <ToastContainer />
        </BrowserRouter>
      </DailryProvider>
    </ModalProvider>
  </StrictMode>,
);
