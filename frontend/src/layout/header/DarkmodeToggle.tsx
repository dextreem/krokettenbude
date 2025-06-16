import { HiOutlineMoon, HiOutlineSun } from "react-icons/hi2";
import useDarkModeState from "../../stores/DarkmodeState";
import ButtonIcon from "../../components/ButtonIcon";

function DarkModeToggle() {
  const { isDarkMode, toggleDarkMode } = useDarkModeState();

  return (
    <ButtonIcon
      title={isDarkMode ? "Set to Light Mode" : "Set to Dark Mode"}
      onClick={toggleDarkMode}
    >
      {isDarkMode ? <HiOutlineSun /> : <HiOutlineMoon />}
    </ButtonIcon>
  );
}

export default DarkModeToggle;
