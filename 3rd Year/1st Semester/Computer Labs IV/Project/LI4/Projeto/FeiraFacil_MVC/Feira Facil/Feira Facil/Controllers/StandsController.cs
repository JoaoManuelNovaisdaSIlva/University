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
    public class StandsController : Controller
    {
        private readonly Feira_FacilContext _context;

        public StandsController(Feira_FacilContext context)
        {
            _context = context;
        }

        // GET: Stands
        public async Task<IActionResult> Index()
        {
              return View(await _context.Stand.ToListAsync());
        }

        // GET: Stands/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null || _context.Stand == null)
            {
                return NotFound();
            }

            var stand = await _context.Stand
                .FirstOrDefaultAsync(m => m.IdStand == id);
            if (stand == null)
            {
                return NotFound();
            }

            return View(stand);
        }

        // GET: Stands/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: Stands/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("IdStand,idVendedor,idCertame")] Stand stand)
        {
            if (ModelState.IsValid)
            {
                _context.Add(stand);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(stand);
        }

        // GET: Stands/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null || _context.Stand == null)
            {
                return NotFound();
            }

            var stand = await _context.Stand.FindAsync(id);
            if (stand == null)
            {
                return NotFound();
            }
            return View(stand);
        }

        // POST: Stands/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("IdStand,idVendedor,idCertame")] Stand stand)
        {
            if (id != stand.IdStand)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(stand);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!StandExists(stand.IdStand))
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
            return View(stand);
        }

        // GET: Stands/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null || _context.Stand == null)
            {
                return NotFound();
            }

            var stand = await _context.Stand
                .FirstOrDefaultAsync(m => m.IdStand == id);
            if (stand == null)
            {
                return NotFound();
            }

            return View(stand);
        }

        // POST: Stands/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            if (_context.Stand == null)
            {
                return Problem("Entity set 'Feira_FacilContext.Stand'  is null.");
            }
            var stand = await _context.Stand.FindAsync(id);
            if (stand != null)
            {
                _context.Stand.Remove(stand);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool StandExists(int id)
        {
          return _context.Stand.Any(e => e.IdStand == id);
        }
    }
}
