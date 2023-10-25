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
    public class VendedoresController : Controller
    {
        private readonly Feira_FacilContext _context;

        public VendedoresController(Feira_FacilContext context)
        {
            _context = context;
        }

        // GET: Vendedors
        public async Task<IActionResult> Index()
        {
            if (HttpContext.Session.GetString("VendedorLogin") == null)
                return RedirectToAction("Login");
            else
                return RedirectToAction("Dashboard");
        }

        public async Task<IActionResult> Login()
        {
            if (HttpContext.Session.GetString("VendedorLogin") != null)
                return RedirectToAction("Dashboard");
            ViewBag.Error = false;
            return View();
        }

        public async Task<IActionResult> Dashboard()
        {
            if (HttpContext.Session.GetString("VendedorLogin") == null)
                return RedirectToAction("Login");

            ViewBag.Error = false;
            return View();
        }

        public async Task<IActionResult> Stands()
        {
            if (HttpContext.Session.GetString("VendedorLogin") == null)
                return RedirectToAction("Login");

            String vendedor = HttpContext.Session.GetString("VendedorLogin");
            String[] paramsVendedor = vendedor.Split(",");

            ViewBag.Error = false;

            ViewBag.Stands =await _context.Certame.Where(c => c.ativo == 1).Join(_context.Stand, std => std.IdCertame, cert => cert.idCertame, (cert, std) => new { idCertame = cert.IdCertame, IdStand =  std.IdStand, ativo = cert.ativo, idVendedor = std.idVendedor }).Where(s => s.idVendedor == int.Parse(paramsVendedor[0])).ToListAsync();
            return View();
        }

        // GET: Vendedors/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null || _context.Vendedor == null)
            {
                return NotFound();
            }

            var vendedor = await _context.Vendedor
                .FirstOrDefaultAsync(m => m.Id == id);
            if (vendedor == null)
            {
                return NotFound();
            }

            return View(vendedor);
        }

        // GET: Vendedors/Create
        public IActionResult Create()
        {
            return View();
        }

        public IActionResult Logout()
        {
            HttpContext.Session.Remove("VendedorLogin");
            return RedirectToAction("Login");
        }

        // POST: Vendedors/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Id,idEmpresa,nome,contactos,morada,password")] Vendedor vendedor)
        {
            if (ModelState.IsValid)
            {
                _context.Add(vendedor);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(vendedor);
        }

        // GET: Vendedors/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null || _context.Vendedor == null)
            {
                return NotFound();
            }

            var vendedor = await _context.Vendedor.FindAsync(id);
            if (vendedor == null)
            {
                return NotFound();
            }
            return View(vendedor);
        }

        // POST: Vendedors/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("Id,idEmpresa,nome,contactos,morada,password")] Vendedor vendedor)
        {
            if (id != vendedor.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(vendedor);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!VendedorExists(vendedor.Id))
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
            return View(vendedor);
        }

        // GET: Vendedors/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null || _context.Vendedor == null)
            {
                return NotFound();
            }

            var vendedor = await _context.Vendedor
                .FirstOrDefaultAsync(m => m.Id == id);
            if (vendedor == null)
            {
                return NotFound();
            }

            return View(vendedor);
        }

        // POST: Vendedors/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            if (_context.Vendedor == null)
            {
                return Problem("Entity set 'Feira_FacilContext.Vendedor'  is null.");
            }
            var vendedor = await _context.Vendedor.FindAsync(id);
            if (vendedor != null)
            {
                _context.Vendedor.Remove(vendedor);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        [HttpPost]
        public IActionResult Login(string nome, string password, string remember)
        {

            var vendedor = _context.Vendedor.Where(v => v.nome == nome && v.password == password)
                .FirstOrDefault();


            if (vendedor != null)
            {
                HttpContext.Session.SetString("VendedorLogin", vendedor.Id + "," + vendedor.nome);
                return RedirectToAction("Dashboard");
            }

            if (vendedor == null)
            {
                ViewBag.Error = true;
                ViewBag.ErrorMessage = "Utilizador não encontrado.";
            }

            return View("Login");

        }

        private bool VendedorExists(int id)
        {
          return _context.Vendedor.Any(e => e.Id == id);
        }
    }
}
