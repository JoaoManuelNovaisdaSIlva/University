using Microsoft.AspNetCore.Components.Forms;
using Microsoft.AspNetCore.Mvc.RazorPages;
using System.Reflection;

namespace FeiraFacil.ViewModels
{
    public class LoginViewModel : PageModel
    {
        public string Email { get; set; }

        public string Password { get; set; }

        public Boolean RememberMe { get; set; }

        public string errorMessage = "";

        public void OnGet()
        {
            Console.Write("OIIIII");
            this.errorMessage = "";
        }

        public void OnPost()
        {
            this.Email = Request.Form["email"];
            this.Password = Request.Form["password"];
            this.RememberMe = (Request.Form["RememberMe"] == 1) ? true : false;

            Console.WriteLine("Recebi info");
        }
    }
}
