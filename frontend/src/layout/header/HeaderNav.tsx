import styled from "styled-components";
import useSessionState from "../../stores/SessionState";
import ButtonIcon from "../../components/ButtonIcon";
import { HiArrowRightOnRectangle, HiCalculator } from "react-icons/hi2";
import { useNavigate } from "react-router-dom";
import DarkModeToggle from "./DarkmodeToggle";
import { ROUTES } from "../../utils/constants";
import { HiLogin, HiUserAdd } from "react-icons/hi";

const Nav = styled.header`
  padding: 1.2rem 2.4rem;

  display: flex;
  gap: 2.4rem;
  align-items: center;
  justify-content: space-between;
`;

const UserIconButton = styled.div`
  border: 1px solid var(--dlt-color-global-neutral-400);
  border-radius: 20px;
  background-color: var(--dlt-color-global-neutral-980);
  color: var(--dlt-color-global-neutral-300);

  cursor: pointer;

  &:hover {
    background-color: var(--dlt-color-global-neutral-800);
  }
`;

const UserIcon = styled.div`
  display: flex;
  align-items: center;
  gap: 0.6rem;
  padding: 0.6rem 1.2rem;

  font-weight: 600;
  font-size: 1.4rem;
`;

function HeaderNav() {
  const userDetails = useSessionState((state) => state.userDetails);
  const token = useSessionState((state) => state.token);
  const resetSession = useSessionState((state) => state.resetSession);
  const navigate = useNavigate();

  const isLoggedIn = token ? true : false;

  function onUserClicked() {
    navigate(`/${ROUTES.USER}`);
  }

  return (
    <Nav>
      <ButtonIcon
        title="RecommendCroquette"
        onClick={() => navigate(`/${ROUTES.RECOMMENDATIONS}`)}
      >
        <HiCalculator />
      </ButtonIcon>

      {userDetails && isLoggedIn && (
        <UserIconButton
          title="Go to User Settings"
          role="button"
          onClick={onUserClicked}
        >
          <UserIcon>
            <span>Hello {userDetails.email}</span>
          </UserIcon>
        </UserIconButton>
      )}

      {!isLoggedIn && (
        <ButtonIcon
          title="Sign Up"
          onClick={() => navigate(`/${ROUTES.SIGNUP}`)}
        >
          <HiUserAdd />
        </ButtonIcon>
      )}

      {!isLoggedIn && (
        <ButtonIcon title="Login" onClick={() => navigate(`/${ROUTES.LOGIN}`)}>
          <HiLogin />
        </ButtonIcon>
      )}

      {isLoggedIn && (
        <ButtonIcon title="Logout" onClick={resetSession}>
          <HiArrowRightOnRectangle />
        </ButtonIcon>
      )}

      <DarkModeToggle />
    </Nav>
  );
}

export default HeaderNav;
