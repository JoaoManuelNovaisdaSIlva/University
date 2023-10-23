using Microsoft.Extensions.Primitives;

namespace FeiraFacil.Pages.Auth
{
    public class LoginViewModel
    {
        public StringValues Email { get; internal set; }
        public StringValues Password { get; internal set; }
        public StringValues Remeber { get; internal set; }
        public string errorMessage { get; internal set; }
    }
}