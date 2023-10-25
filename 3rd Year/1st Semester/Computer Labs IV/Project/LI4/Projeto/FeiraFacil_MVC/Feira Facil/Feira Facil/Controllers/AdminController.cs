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
    public class AdminController : Controller
    {
        private readonly Feira_FacilContext _context;

        public AdminController(Feira_FacilContext context)
        {
            _context = context;
        }

        // GET: Admin
        public async Task<IActionResult> Index()
        {
            if (HttpContext.Session.GetString("AdminLogin") != null)
                return RedirectToAction("Dashboard");
            return RedirectToAction("Login");
        }

        public async Task<IActionResult> Login()
        {
            if (HttpContext.Session.GetString("AdminLogin") != null)
                return RedirectToAction("Dashboard");

            ViewBag.Error = false;
            return View(await _context.Admin.ToListAsync());
        }

        public async Task<IActionResult> Dashboard()
        {
            if (HttpContext.Session.GetString("AdminLogin") == null)
                return RedirectToAction("Login");
            ViewBag.Error = false;
            return View();
        }

        public async Task<IActionResult> Feiras()
        {
            if (HttpContext.Session.GetString("AdminLogin") == null)
                return RedirectToAction("Login");


            ViewBag.Error = false;
            return View(await _context.Certame.Where(c => c.ativo == 1).ToListAsync());
        }



        public async Task<IActionResult> Utilizadores()
        {
            if (HttpContext.Session.GetString("AdminLogin") == null)
                return RedirectToAction("Login");

            ViewBag.Error = false;
            return View(await _context.User.ToListAsync());
        }

        public async Task<IActionResult> Empresas()
        {
            if (HttpContext.Session.GetString("AdminLogin") == null)
                return RedirectToAction("Login");

            ViewBag.Error = false;
            return View(await _context.Empresa.ToListAsync());
        }

        // GET: Admin/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null || _context.Admin == null)
            {
                return NotFound();
            }

            var admin = await _context.Admin
                .FirstOrDefaultAsync(m => m.Id == id);
            if (admin == null)
            {
                return NotFound();
            }

            return View(admin);
        }

        // GET: Admin/Create
        public IActionResult Create()
        {
            return View();
        }

        public IActionResult CriarCertame()
        {
            return View();
        }

        // POST: Admin/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,email,password,nome")] Admin admin)
        {
            if (ModelState.IsValid)
            {
                _context.Add(admin);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(admin);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> CriarCertame([Bind("IdCertame,maxStandsCertame,categoraCertame")] Certame certame)
        {
            certame.ativo = 1;
            if (ModelState.IsValid)
            {
                _context.Add(certame);
                await _context.SaveChangesAsync();
                return RedirectToAction("Dashboard");
            }
            return View(certame);
        }

        // GET: Admin/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null || _context.Admin == null)
            {
                return NotFound();
            }

            var admin = await _context.Admin.FindAsync(id);
            if (admin == null)
            {
                return NotFound();
            }
            return View(admin);
        }

        // POST: Admin/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("Id,email,password,nome")] Admin admin)
        {
            if (id != admin.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(admin);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!AdminExists(admin.Id))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            return View(admin);
        }

        // GET: Admin/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null || _context.Admin == null)
            {
                return NotFound();
            }

            var admin = await _context.Admin
                .FirstOrDefaultAsync(m => m.Id == id);
            if (admin == null)
            {
                return NotFound();
            }

            return View(admin);
        }

        [HttpPost]
        public IActionResult Login(string email, string password, string remember)
        {

            var admin = _context.Admin.Where(e => e.email == email && e.password == password)
                .FirstOrDefault();


            if (admin != null)
            {
                HttpContext.Session.SetString("AdminLogin", admin.Id + "," + admin.nome);
                return RedirectToAction("Dashboard");
            }

            if (admin == null)
            {
                ViewBag.Error = true;
                ViewBag.ErrorMessage = "Utilizador não encontrado.";
            }

            return View("Login");

        }

        public async Task<IActionResult> RemoverCertame(int id)
        {

            var entity = await _context.Certame.FindAsync(id);
            if (entity == null)
            {
                return View("Error");
            }


            entity.ativo = 0;
            _context.Certame.Update(entity);
            await _context.SaveChangesAsync();

            return RedirectToAction("Dashboard");

        }

        // POST: Admin/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            if (_context.Admin == null)
            {
                return Problem("Entity set 'Feira_FacilContext.Admin'  is null.");
            }
            var admin = await _context.Admin.FindAsync(id);
            if (admin != null)
            {
                _context.Admin.Remove(admin);
            }

            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool AdminExists(int id)
        {
            return _context.Admin.Any(e => e.Id == id);
        }
    }
}
