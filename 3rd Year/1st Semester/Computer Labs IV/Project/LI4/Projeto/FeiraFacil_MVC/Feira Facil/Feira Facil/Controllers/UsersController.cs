using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Feira_Facil.Data;
using Feira_Facil.Models;

namespace Feira_Facil.Controllers
{
    public class UsersController : Controller
    {
        private readonly Feira_FacilContext _context;

        public UsersController(Feira_FacilContext context)
        {
            _context = context;
        }

        // GET: Users
        public async Task<IActionResult> Index()
        {
              return View(await _context.User.ToListAsync());
        }

        // GET: Users/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null || _context.User == null)
            {
                return NotFound();
            }

            var user = await _context.User
                .FirstOrDefaultAsync(m => m.Id == id);
            if (user == null)
            {
                return NotFound();
            }

            return View(user);
        }

        // GET: Users/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: Users/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,email,password,firstName,lastName,phone,nif,iban,morada")] User user)
        {
            if (ModelState.IsValid)
            {
                _context.Add(user);
                await _context.SaveChangesAsync();
                return RedirectToAction("Utilizadores", "Admin");
            }
            return View(user);
        }

        // GET: Users/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (HttpContext.Session.GetString("AdminLogin") == null)
                return RedirectToAction("Login", "Admin");

            if (id == null || _context.User == null)
            {
                return NotFound();
            }

            var user = await _context.User.FindAsync(id);
            if (user == null)
            {
                return NotFound();
            }
            return View(user);
        }

        // POST: Users/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("Id,email,password,firstName,lastName,phone,nif,iban,morada")] User user)
        {
            if (id != user.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(user);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!UserExists(user.Id))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction("Utilizadores", "Admin");
            }
            return View(user);
        }

        // GET: Users/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (HttpContext.Session.GetString("AdminLogin") == null)
                return RedirectToAction("Login", "Admin");

            if (_context.User == null)
            {
                return Problem("Entity set 'Feira_FacilContext.User'  is null.");
            }
            var user = await _context.User.FindAsync(id);
            if (user != null)
            {
                _context.User.Remove(user);
            }

            await _context.SaveChangesAsync();
            return RedirectToAction("Utilizadores", "Admin");
        }



        private bool UserExists(int id)
        {
          return _context.User.Any(e => e.Id == id);
        }

        /* GETs */
        public IActionResult Login()
        {
			ViewBag.Error = false;

			//Verifica se o utiizador já está ligado
			if (HttpContext.Session.GetString("User") != null)
                return RedirectToAction("Dashboard");

            return View();
        }

        public IActionResult Register()
        {
            //Verifica se o utiizador já está ligado
            if (HttpContext.Session.GetString("User") != null)
                return RedirectToAction("Dashboard");

            ViewBag.Error = false;
            return View();
        }

        public IActionResult Dashboard()
        {
            if (HttpContext.Session.GetString("User") == null)
                return RedirectToAction("Login");
            String user = HttpContext.Session.GetString("User");
            ViewBag.nomeCompleto = user.Split(",")[1];
            return View();
        }

        public IActionResult Carrinho()
        {
            
            return View();
        }

        public IActionResult Logout()
        {
            HttpContext.Session.Remove("User");
            return RedirectToAction("Login");
        }

        /* POSTs */

        [HttpPost]
        public IActionResult Login(string email, string password, string remember)
        {

            var user = _context.User.Where(e => e.email == email && e.password == password)
                .FirstOrDefault();

            
            if(user != null)
            {
                int month = DateTime.Now.Month;
                int day = DateTime.Now.Day;
                string token = ((day * 100 + month) * 700 + day * 13).ToString();
                HttpContext.Session.SetString("User", user.Id + "," + user.firstName + "," + user.lastName);
                return RedirectToAction("Dashboard");
            }

            if (user == null)
            {
                ViewBag.Error = true;
                ViewBag.ErrorMessage = "Utilizador não encontrado.";
            }

            return View("Login");

        }



        [HttpPost]
        public async Task<IActionResult> Register(string email, string password, string firstName, string lastName,
                                        string phone)
        {

            User u = new User();
            u.email = email;
            u.password = password;
            u.firstName = firstName;
            u.lastName = lastName;
            u.phone = phone;

            var user = _context.User.Add(u);
            int resp = await _context.SaveChangesAsync();

            if(resp > 0)
            {
                HttpContext.Session.SetString("User", resp + "," + firstName + "," + lastName);

                return RedirectToAction("Dashboard");
            }


            return View("Login");
        }





    }
}
