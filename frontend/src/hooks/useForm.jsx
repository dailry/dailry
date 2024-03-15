import { useFormik } from 'formik';

const useForm = (formikOptions) => {
  const { initialValues, handleSubmit, validationSchema, ...rest } =
    formikOptions;

  const form = useFormik({
    initialValues,
    onSubmit: handleSubmit,
    validationSchema,
    ...rest,
  });

  const handleChangeForm = (e) => {
    form.setFieldTouched(e.target.name);
    form.handleChange(e);
  };

  return { form, handleChangeForm };
};

export default useForm;
