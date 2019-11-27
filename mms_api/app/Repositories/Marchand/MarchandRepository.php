<?php

namespace App\Repositories\Marchand;

use App\Models\Marchand;
use App\Repositories\Marchand\Interfaces\MarchandRepositoryInterface;


class MarchandRepository implements MarchandRepositoryInterface
{
    protected $marchand;

    public function __construct(Marchand $marchand)
    {
        $this->marchand = $marchand;
    }
    /**
     * Get a marchand by it's ID
     *
     * @param int
     * @return collection
     */

    public function getById($id)
    {
        return $this->marchand->findOrfail($id);
    }

    /**
     * Get a marchand by it's ID
     *
     * @param string
     * @return collection
     */

    /**
     * Get's all marchands.
     *
     * @return mixed
     */
    public function all()
    {
        return $this->marchand->paginate();
    }

    /**
     * Create marchand.
     *
     * @return mixed
     */
    public function create(array $marchand_data)
    {
       

    }

    /**
     * Delete a marchand.
     *
     * @param int
     */
    public function delete($id)
    {
        $this->marchand->findOrfail($id)->delete();
        
    }

    /**
     * Update a marchand.
     *
     * @param int
     * @param array
     */
    public function update($id, array $marchand_data)
    {
    }

}

