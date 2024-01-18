import PropTypes from 'prop-types';
import Input from '../../components/common/Input/Input';
import Button from '../../components/common/Button/Button';
import Text from '../../components/common/Text/Text';
import { TEXT } from '../../styles/color';
import * as S from './JoinPage.styled';
import useJoinForm from '../../hooks/useJoinForm';

const JoinForm = (props) => {
  const { setJoinCompletedMember } = props;
  const {
    joinForm,
    handleChangeForm,
    isUserNameDuplicated,
    isNickNameDuplicated,
    checkIsNickNameDuplicated,
    checkIsUserNameDuplicated,
  } = useJoinForm(setJoinCompletedMember);

  const { values, touched, errors, handleSubmit } = joinForm;

  return (
    <S.JoinFormWrapper onSubmit={handleSubmit}>
      <div>
        <Input
          id="username"
          name="username"
          placeholder={'아이디'}
          onChange={handleChangeForm}
          value={values.username}
        >
          <Button size={'sm'} type="button" onClick={checkIsUserNameDuplicated}>
            중복확인
          </Button>
        </Input>
        <div style={{ position: 'absolute', marginTop: '4px' }}>
          {errors.username && touched.username && (
            <Text size={12} color={TEXT.error}>
              {errors.username}
            </Text>
          )}

          {!errors.username && (
            <>
              {isUserNameDuplicated === undefined && values.username && (
                <Text size={12} color={TEXT.error}>
                  아이디를 중복 확인 해주세요
                </Text>
              )}
              {isUserNameDuplicated === true && (
                <Text size={12} color={TEXT.error}>
                  이미 사용중인 아이디입니다
                </Text>
              )}
              {isUserNameDuplicated === false && (
                <Text size={12} color={TEXT.valid}>
                  사용 가능한 아이디입니다
                </Text>
              )}
            </>
          )}
        </div>
      </div>

      <div>
        <Input
          id="nickname"
          name="nickname"
          placeholder={'닉네임'}
          onChange={handleChangeForm}
          value={values.nickname}
        >
          <Button size={'sm'} type="button" onClick={checkIsNickNameDuplicated}>
            중복확인
          </Button>
        </Input>

        <div style={{ position: 'absolute', marginTop: '4px' }}>
          {errors.nickname && touched.nickname && (
            <Text size={12} color={TEXT.error}>
              {errors.nickname}
            </Text>
          )}

          {!errors.nickname && (
            <>
              {isNickNameDuplicated === undefined && values.nickname && (
                <Text size={12} color={TEXT.error}>
                  닉네임을 중복 확인 해주세요
                </Text>
              )}
              {isNickNameDuplicated === true && (
                <Text size={12} color={TEXT.error}>
                  이미 사용중인 닉네임입니다
                </Text>
              )}
              {isNickNameDuplicated === false && (
                <Text size={12} color={TEXT.valid}>
                  사용 가능한 닉네임입니다
                </Text>
              )}
            </>
          )}
        </div>
      </div>
      <div>
        <Input
          id="password"
          name="password"
          placeholder={'비밀번호'}
          type="password"
          onChange={handleChangeForm}
          value={values.password}
        />
        <div style={{ position: 'absolute', marginTop: '4px' }}>
          {errors.password && touched.password && (
            <Text size={12} color={TEXT.error}>
              {errors.password}
            </Text>
          )}

          {!errors.password && touched.password && (
            <Text size={12} color={TEXT.valid}>
              사용 가능한 비밀번호 입니다.
            </Text>
          )}
        </div>
      </div>
      <div>
        <Input
          id="checkPassword"
          name="checkPassword"
          type="password"
          placeholder={'비밀번호 확인'}
          onChange={handleChangeForm}
          value={values.checkPassword}
        />
        <div style={{ position: 'absolute', marginTop: '4px' }}>
          {errors.checkPassword && touched.checkPassword && (
            <Text size={12} color={TEXT.error}>
              {errors.checkPassword}
            </Text>
          )}
          {!errors.checkPassword && (
            <>
              {values.checkPassword !== values.password && (
                <Text size={12} color={TEXT.error}>
                  비밀번호가 일치하지 않습니다
                </Text>
              )}
              {!errors.password &&
                values.checkPassword === values.password &&
                touched.checkPassword && (
                  <Text size={12} color={TEXT.valid}>
                    비밀번호가 일치합니다.
                  </Text>
                )}
            </>
          )}
        </div>
      </div>

      <Button type="submit">회원가입</Button>
    </S.JoinFormWrapper>
  );
};

JoinForm.propTypes = {
  setJoinCompletedMember: PropTypes.func,
};

export default JoinForm;
