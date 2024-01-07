import { useState } from 'react';

const useForm = (initialForm) => {
  const [form, setForm] = useState(initialForm);
  const handleChangeFormValue = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  return [form, handleChangeFormValue];
};

export default useForm;
