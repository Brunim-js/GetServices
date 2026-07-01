(function () {
  function iniciarTema() {
    const temaSalvo = localStorage.getItem("getservices-tema") || "claro";

    if (temaSalvo === "escuro") {
      document.body.classList.add("dark-mode");
    }

    let rootElement = document.getElementById("theme-toggle-root");
    if (!rootElement) {
      rootElement = document.createElement("div");
      rootElement.id = "theme-toggle-root";
      document.body.appendChild(rootElement);
    }

    function ThemeToggle() {
      const [darkMode, setDarkMode] = React.useState(
        localStorage.getItem("getservices-tema") === "escuro"
      );

      React.useEffect(function () {
        if (darkMode) {
          document.body.classList.add("dark-mode");
          localStorage.setItem("getservices-tema", "escuro");
        } else {
          document.body.classList.remove("dark-mode");
          localStorage.setItem("getservices-tema", "claro");
        }
      }, [darkMode]);

      return React.createElement(
        "button",
        {
          type: "button",
          className: "theme-toggle-btn",
          onClick: function () {
            setDarkMode(!darkMode);
          },
          "aria-label": "Alternar modo claro e escuro"
        },
        darkMode ? "☀️ Modo claro" : "🌙 Modo escuro"
      );
    }

    ReactDOM.createRoot(rootElement).render(React.createElement(ThemeToggle));
  }

  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", iniciarTema);
  } else {
    iniciarTema();
  }
})();
