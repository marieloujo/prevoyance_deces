<?php

namespace App\Repositories\SuperMarchand;

use App\Models\SuperMarchand;
use App\Repositories\SuperMarchand\Interfaces\SuperMarchandRepositoryInterface;


class SuperMarchandRepository implements SuperMarchandRepositoryInterface
{
    protected $superMarchand;

    public function __construct(SuperMarchand $superMarchand)
    {
        $this->superMarchand = $superMarchand;
    }
    /**
     * Get a superMarchand by it's ID
     *
     * @param int
     * @return collection
     */

    public function getById($id)
    {
        return $this->superMarchand->findOrfail($id);
    }

    /**
     * Get a superMarchand by it's ID
     *
     * @param string
     * @return collection
     */

    /**
     * Get's all superMarchands.
     *
     * @return mixed
     */
    public function all()
    {
        return $this->superMarchand->paginate();
    }

    /**
     * Create superMarchand.
     *
     * @return mixed
     */
    public function create(array $superMarchand_data)
    {
        $superMarchand = new SuperMarchand();
        $superMarchand->matricule = $superMarchand_data['matricule'];
        $superMarchand->commission = $superMarchand_data['commission'];
        $superMarchand->save();

        return $superMarchand;
    }

    /**
     * Delete a superMarchand.
     *
     * @param int
     */
    public function delete($id)
    {
        $this->superMarchand->findOrfail($id)->delete();
        
    }

    /**
     * Update a superMarchand.
     *
     * @param int
     * @param array
     */
    public function update($id, array $superMarchand_data)
    {
        $superMarchand =  $this->superMarchand->findOrfail($id);
        $superMarchand->matricule = $superMarchand_data['matricule'];
        $superMarchand->commission = $superMarchand_data['commission'];
        $superMarchand->update();
    }

}
//SuperSuperMarchand
