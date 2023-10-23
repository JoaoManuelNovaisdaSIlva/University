using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Feira_Facil.Data;
using Feira_Facil.Models;
using System.Configuration;
using NuGet.Packaging;
using System.Collections.Specialized;

namespace Feira_Facil.Controllers
{
    public class CertamesController : Controller
    {
        private readonly Feira_FacilContext _context;

        public CertamesController(Feira_FacilContext context)
        {
            _context = context;
        }

        // GET: Certames
        public async Task<IActionResult> Index()
        {
            var ret = await _context.Certame.Where(c => c.ativo == 1).ToListAsync();
            var contaStands = new Dictionary<int, int>();

			foreach (Certame c in ret)
            {
                contaStands.Add(c.IdCertame, _context.Stand.Where(s => s.idCertame == c.IdCertame).Count());
            }

            ViewBag.contaStands = contaStands;
            return View(ret);
        }

		public async Task<IActionResult> Ver(int? id)
		{
			if (id == null || _context.Certame == null)
			{
				return NotFound();
			}

            var stands = await _context.Stand.Where(s => s.idCertame == id).ToListAsync();
            var produtos = new List<String>();
            ViewBag.Produtos = (await _context.Produto.
                                Join(_context.Vendedor, p => p.idVendedor,
                                v => v.Id, (p, v) => new { p, v })
                                .Join(_context.Stand, s => s.p.idStand,
                                c => c.IdStand, (s,c) => new { nome = s.p.nome, nome_vendedor = s.v.nome, id_stand = s.p.idStand, id_certame = c.idCertame, imagens = s.p.imagens }).Where(z => z.id_certame == id)
                                .ToListAsync());



            return View();
		}

		// GET: Certames/Details/5
		public async Task<IActionResult> Details(int? id)
        {
            if (id == null || _context.Certame == null)
            {
                return NotFound();
            }

            var certame = await _context.Certame
                .FirstOrDefaultAsync(m => m.IdCertame == id);
            if (certame == null)
            {
                return NotFound();
            }

            return View(certame);
        }

        // GET: Certames/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: Certames/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("IdCertame,maxStandsCertame,categoraCertame")] Certame certame)
        {
            if (ModelState.IsValid)
            {
                _context.Add(certame);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(certame);
        }

        // GET: Certames/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null || _context.Certame == null)
            {
                return NotFound();
            }

            var certame = await _context.Certame.FindAsync(id);
            if (certame == null)
            {
                return NotFound();
            }
            return View(certame);
        }

        // POST: Certames/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("IdCertame,maxStandsCertame,categoraCertame")] Certame certame)
        {
            if (id != certame.IdCertame)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(certame);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!CertameExists(certame.IdCertame))
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
            return View(certame);
        }

        // GET: Certames/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null || _context.Certame == null)
            {
                return NotFound();
            }

            var certame = await _context.Certame
                .FirstOrDefaultAsync(m => m.IdCertame == id);
            if (certame == null)
            {
                return NotFound();
            }

            return View(certame);
        }

        // POST: Certames/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            if (_context.Certame == null)
            {
                return Problem("Entity set 'Feira_FacilContext.Certame'  is null.");
            }
            var certame = await _context.Certame.FindAsync(id);
            if (certame != null)
            {
                _context.Certame.Remove(certame);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool CertameExists(int id)
        {
          return _context.Certame.Any(e => e.IdCertame == id);
        }
    }
}
