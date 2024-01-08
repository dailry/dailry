import { useState } from 'react';
import PropTypes from 'prop-types';
import useForm from '../../hooks/useForm';
import { getCheckUserName, postJoinMember } from '../../apis/memberApi';
import Input from '../../components/common/Input/Input';
import AuthButton from '../../components/common/AuthButton/AuthButton';
import Text from '../../components/common/Text/Text';
import { TEXT } from '../../styles/color';

const JoinForm = (props) => {
  const { setJoinCompletedMember } = props;
  const [form, handleChangeFormValue] = useForm({
    username: '',
    password: '',
    nickname: '',
  });
  const [checkPassword, setCheckPassword] = useState('');
  const [isUserNameDuplicated, setIsUserNameDuplicated] = useState(undefined);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (checkPassword !== form.password || isUserNameDuplicated) return;

    await postJoinMember(form);
    setJoinCompletedMember({ nickname: form.nickname });
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <Input
          name="username"
          placeholder={'아이디'}
          onChange={handleChangeFormValue}
        >
          <AuthButton
            size={'sm'}
            type="button"
            onClick={async () => {
              const result = await getCheckUserName(form.username);
              setIsUserNameDuplicated(result);
            }}
          >
            중복확인
          </AuthButton>
        </Input>
        <Text size={12} color={TEXT.error}>
          아이디 중복확인을 해주세요
        </Text>
      </div>
      <div>
        <Input
          name="nickname"
          placeholder={'닉네임'}
          onChange={handleChangeFormValue}
        ></Input>
      </div>
      <div>
        <Input
          name="password"
          placeholder={'비밀번호'}
          type="password"
          onChange={handleChangeFormValue}
        />
        <Text size={12} color={TEXT.valid}>
          사용 가능한 비밀번호입니다
        </Text>
      </div>
      <div>
        <Input
          placeholder={'비밀번호 확인'}
          type="password"
          onChange={(e) => setCheckPassword(e.target.value)}
        />
        <Text size={12} color={TEXT.error}>
          비밀번호가 비밀번호 확인과 다릅니다
        </Text>
      </div>
      <AuthButton type="submit">회원가입</AuthButton>
    </form>
  );
};

JoinForm.propTypes = {
  setJoinCompletedMember: PropTypes.func,
};

export default JoinForm;
