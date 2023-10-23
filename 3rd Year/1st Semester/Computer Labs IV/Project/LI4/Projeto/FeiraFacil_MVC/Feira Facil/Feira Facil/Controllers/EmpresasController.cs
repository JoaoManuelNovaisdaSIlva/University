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
    public class EmpresasController : Controller
    {
        private readonly Feira_FacilContext _context;

        public EmpresasController(Feira_FacilContext context)
        {
            _context = context;
        }

        // GET: Empresas
        public async Task<IActionResult> Index()
        {
              return View(await _context.Empresa.ToListAsync());
        }


        // GET: Empresas/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null || _context.Empresa == null)
            {
                return NotFound();
            }

            var empresa = await _context.Empresa
                .FirstOrDefaultAsync(m => m.IdEmpresa == id);
            if (empresa == null)
            {
                return NotFound();
            }

            return View(empresa);
        }

        public IActionResult Login()
        {
			if (HttpContext.Session.GetString("LoginEmpresa") != null)
				return RedirectToAction("Dashboard");

			ViewBag.Error = false;
            return View();
        }

        public IActionResult Dashboard()
        {

            if (HttpContext.Session.GetString("LoginEmpresa") == null)
                return RedirectToAction("Login");


            String empresa = HttpContext.Session.GetString("LoginEmpresa");
            ViewBag.nomeCompleto = "asdasd";
            return View();
        }

        public async Task<IActionResult> CriarStand()
        {

            if (HttpContext.Session.GetString("LoginEmpresa") == null)
                return RedirectToAction("Login");


            String empresa = HttpContext.Session.GetString("LoginEmpresa");
            String[] paramsEmpresa = empresa.Split(",");
            ViewBag.Info = empresa;
            ViewBag.feiras = await _context.Certame.Where(c => c.ativo == 1).ToListAsync();
            ViewBag.vendedores = await _context.Vendedor.Where(v => v.idEmpresa == int.Parse(paramsEmpresa[0])).ToListAsync();

            return View();
        }

        public async Task<IActionResult> AdicionarVendedor()
        {
            ViewBag.Error = false;

            if (HttpContext.Session.GetString("LoginEmpresa") == null)
                return RedirectToAction("Login");


            String empresa = HttpContext.Session.GetString("LoginEmpresa");
            String[] paramsEmpresa = empresa.Split(",");
            ViewBag.Info = empresa;
            ViewBag.users = await _context.User.ToListAsync();
            ViewBag.Error = false;
            return View();
        }

        // GET: Empresas/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: Empresas/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("IdEmpresa,nome,nif,morada,contactos,loginUsername,loginPassword")] Empresa empresa)
        {
            if (ModelState.IsValid)
            {
                _context.Add(empresa);
                await _context.SaveChangesAsync();
                return RedirectToAction("Empresas", "Admin");
            }
            return View(empresa);
        }

        // GET: Empresas/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null || _context.Empresa == null)
            {
                return NotFound();
            }

            var empresa = await _context.Empresa.FindAsync(id);
            if (empresa == null)
            {
                return NotFound();
            }
            return View(empresa);
        }

        // POST: Empresas/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("IdEmpresa,nome,nif,morada,contactos")] Empresa empresa)
        {
            if (id != empresa.IdEmpresa)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(empresa);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!EmpresaExists(empresa.IdEmpresa))
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
            return View(empresa);
        }

        // GET: Empresas/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (_context.Empresa == null)
            {
                return Problem("Entity set 'Feira_FacilContext.Empresa'  is null.");
            }
            var empresa = await _context.Empresa.FindAsync(id);
            if (empresa != null)
            {
                _context.Empresa.Remove(empresa);
            }

            await _context.SaveChangesAsync();
            return RedirectToAction("Empresas", "Admin");
        }



        /* POSTs */

        [HttpPost]
        public IActionResult Login(string email, string password, string remember)
        {

            var empresa = _context.Empresa.Where(e => e.loginUsername == email && e.loginPassword== password)
                .FirstOrDefault();


            if (empresa != null)
            {
                int month = DateTime.Now.Month;
                int day = DateTime.Now.Day;
                string token = ((day * 100 + month) * 700 + day * 13).ToString();
                HttpContext.Session.SetString("LoginEmpresa", empresa.IdEmpresa + "," + empresa.nome+ "," + empresa.nif);
                return RedirectToAction("Dashboard");
            }

            if (empresa == null)
            {
                ViewBag.Error = true;
                ViewBag.ErrorMessage = "Empresa não encontrado.";
            }

            return View("Login");

        }

        [HttpPost]
        public async Task<IActionResult> AdicionarVendedor(string nome, string password, string passwordConfirm, string contactos,
                                        string morada)
        {
            String empresa = HttpContext.Session.GetString("LoginEmpresa");
            String[] paramsEmpresa = empresa.Split(",");

            if (password == passwordConfirm)
            {
                Vendedor v = new Vendedor();
                v.nome = nome;
                v.password = password;
                v.idEmpresa = int.Parse(paramsEmpresa[0]);
                v.contactos = contactos;
                v.morada = morada;

                var vendedor = _context.Vendedor.Add(v);
                int resp = await _context.SaveChangesAsync();

                if (resp > 0)
                {
                    return RedirectToAction("Dashboard");
                }
                
            }
            else
            {
                ViewBag.Message = "Please confirm passwords";
                ViewBag.Error = true;
            }
            


            return View("AdicionarVendedor");
        }

        [HttpPost]
        public async Task<IActionResult> CriarStand(int escolhaFeira, int escolhaVendedor)
        {
            String empresa = HttpContext.Session.GetString("LoginEmpresa");
            String[] paramsEmpresa = empresa.Split(",");

            Stand s = new Stand();
            s.idCertame = escolhaFeira;
            s.idVendedor = escolhaVendedor;

            var certame = await _context.Certame.Where(c => c.IdCertame == escolhaFeira).FirstOrDefaultAsync();
            if (certame.ativo != 1)
            {
                ViewBag.Error = true;
                ViewBag.Message = "Não está ativa essa feira.";
                return View("CriarStand");
            }

            if(_context.Stand.Where(s => s.idCertame == escolhaFeira).Count() > certame.maxStandsCertame)
            {
                ViewBag.Error = true;
                ViewBag.Message = "Número máximo de stands ultrapssado..";
                return View("CriarStand");
            }

            

            var a = _context.Stand.Add(s);
            int resp = await _context.SaveChangesAsync();

            if (resp > 0)
            {
                
                return RedirectToAction("Dashboard");
            }
            

            



            return View("AdicionarVendedor");
        }


        private bool EmpresaExists(int id)
        {
          return _context.Empresa.Any(e => e.IdEmpresa == id);
        }
    }
}
